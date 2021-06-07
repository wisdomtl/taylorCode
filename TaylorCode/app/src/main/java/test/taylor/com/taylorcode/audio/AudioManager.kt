package test.taylor.com.taylorcode.audio

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaRecorder
import android.os.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * provide the ability to record audio in file.
 * [AudioManager] exists for the sake of the following:
 * 1. launch a thread to record audio in file.
 * 2. control the state of recording and invoke according callbacks in main thread.
 * 3. provide interface for the business layer to control audio recording
 */
class AudioManager(val context: Context, val type: String = AAC) :
    CoroutineScope by CoroutineScope(SupervisorJob() + Executors.newFixedThreadPool(1).asCoroutineDispatcher()) {
    companion object {
        const val AAC = "aac"
        const val AMR = "amr"
        const val PCM = "pcm"
    }

    private val ACTION_START_RECORD = 1
    private val ACTION_STOP_RECORD = 2

    private val RECORD_FAILED = 1
    private val RECORD_READY = 2
    private val RECORD_START = 3
    private val RECORD_SUCCESS = 4
    private val RECORD_CANCELED = 5
    private val RECORD_REACH_MAX_TIME = 6

    /**
     * the recording state
     */
    internal val STATE_REACH_MAX_TIME = 1
    internal val STATE_ERROR = - 1

    /**
     * the callback business layer cares about
     */
    var onRecordReady: (() -> Unit)? = null
    var onRecordStart: ((File) -> Unit)? = null

    // deliver audio file and duration to business layer
    var onRecordSuccess: ((File, Long) -> Unit)? = null
    var onRecordFail: (() -> Unit)? = null
    var onRecordCancel: (() -> Unit)? = null
    var onRecordReachedMaxTime: ((Int) -> Unit)? = null

    /**
     * deliver recording state to business layer
     */
    private val callbackHandler = Handler(Looper.getMainLooper())

    var maxDuration = 120 * 1000

    private var duration = 0L

    private var listener = object : InfoListener {
        override fun onInfo(state: Int, extra: Int) {
            when (state) {
                STATE_REACH_MAX_TIME -> {
                    recorder.stop()
                    handleRecordEnd(true, maxDuration.toLong(), true)// is this right
                }
                STATE_ERROR -> {
                    handleRecordEnd(false, 0, false)
                }
            }
        }
    }

    private var recorder: Recorder = AudioRecorder(listener, type)
    private var audioFile: File? = null
    private var cancelRecord: AtomicBoolean = AtomicBoolean(false)
    private val audioManager: AudioManager = context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun start(maxDuration: Int = 120) {
        this.maxDuration = maxDuration * 1000
        startRecord()
    }

    fun stop(cancel: Boolean = false) {
        stopRecord(cancel)
    }

    fun release() {
        launch { recorder.release() }
    }

    fun isRecording() = recorder.isRecording()

    private fun startRecord() {
        audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)

        if (recorder.isRecording()) {
            setState(RECORD_FAILED)
            return
        }

        if (getAudioFreeSpace() <= 0) {
            setState(RECORD_FAILED)
            return
        }

        audioFile = getAudioFile()
        if (audioFile == null) setState(RECORD_FAILED)

        cancelRecord.set(false)
        try {
            if (! cancelRecord.get()) {
                setState(RECORD_READY)
                if (hasPermission()) {
                    launch { recorder.start(audioFile !!, maxDuration) }
                    setState(RECORD_START)
                } else {
                    stopRecord(false)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stopRecord(false)
        }

        if (! recorder.isRecording()) {
            setState(RECORD_FAILED)
        }
    }

    private fun hasPermission(): Boolean {
        return context.checkCallingOrSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && context.checkCallingOrSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun stopRecord(cancel: Boolean) {
        if (! recorder.isRecording()) {
            return
        }
        cancelRecord.set(cancel)
        audioManager.abandonAudioFocus(null)
        var duration = 0L
        try {
            duration = recorder.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            handleRecordEnd(true, duration, false)
        }
    }

    private fun handleRecordEnd(isSuccess: Boolean, duration: Long, isReachMaxTime: Boolean) {
        this.duration = duration
        if (cancelRecord.get()) {
            audioFile?.deleteOnExit()
            setState(RECORD_CANCELED)
        } else if (! isSuccess) {
            audioFile?.deleteOnExit()
            setState(RECORD_FAILED)
        } else {
            if (isAudioFileInvalid()) {
                setState(RECORD_FAILED)
                if (isReachMaxTime) {
                    setState(RECORD_REACH_MAX_TIME)
                }
            } else {
                setState(RECORD_SUCCESS)
            }
        }
    }

    private fun isAudioFileInvalid() = audioFile == null || ! audioFile !!.exists() || audioFile !!.length() <= 0

    private fun setState(state: Int) {
        callbackHandler.post {
            when (state) {
                RECORD_FAILED -> onRecordFail?.invoke()
                RECORD_READY -> onRecordReady?.invoke()
                RECORD_START -> audioFile?.let { onRecordStart?.invoke(it) }
                RECORD_CANCELED -> onRecordCancel?.invoke()
                RECORD_SUCCESS -> audioFile?.let { onRecordSuccess?.invoke(it, duration) }
                RECORD_REACH_MAX_TIME -> onRecordReachedMaxTime?.invoke(maxDuration)
            }
        }
    }

    private fun getAudioFreeSpace(): Long {
        if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
            return 0L
        }

        return try {
            val stat = StatFs(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath)
            stat.run { blockSizeLong * availableBlocksLong }
        } catch (e: Exception) {
            0L
        }
    }

    private fun getAudioFile(): File? {
        val audioFilePath = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath
        if (audioFilePath.isNullOrEmpty()) return null
        return File("$audioFilePath${File.separator}${UUID.randomUUID()}.$type")
    }

    /**
     * the implementation of [Recorder] define the detail of how to record audio.
     * [AudioManager] works with [Recorder] and dont care about the recording details
     */
    interface Recorder {

        /**
         * audio output format
         */
        var outputFormat: String

        /**
         * deliver the information of audio to [AudioManager]
         */
        var infoListener: InfoListener

        /**
         * whether audio is recording
         */
        fun isRecording(): Boolean

        /**
         * start audio recording, it is time-consuming
         */
        suspend fun start(outputFile: File, maxDuration: Int)

        /**
         * stop audio recording
         * @return the duration of audio
         */
        fun stop(): Long

        /**
         * release the resource of audio recording
         */
        fun release()
    }

    /**
     * deliver the information of audio to [AudioManager]
     */
    interface InfoListener {
        fun onInfo(state: Int, extra: Int)
    }

    /**
     * record audio by [android.media.MediaRecorder]
     */
    inner class MediaRecord(override var infoListener: InfoListener, override var outputFormat: String) : Recorder {
        private var starTime: Long = 0L
        private val listener = MediaRecorder.OnInfoListener { _, what, extra ->
            val state = when (what) {
                MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED -> STATE_REACH_MAX_TIME
                else -> STATE_ERROR
            }
            infoListener.onInfo(state, extra)
        }
        private val errorListener = MediaRecorder.OnErrorListener { _, _, extra ->
            infoListener.onInfo(STATE_ERROR, extra)
        }
        private val recorder = MediaRecorder()
        private var isRecording = AtomicBoolean(false)
        override fun isRecording(): Boolean = isRecording.get()

        override suspend fun start(outputFile: File, maxDuration: Int) {
            val format = when (outputFormat) {
                AMR -> MediaRecorder.OutputFormat.AMR_NB
                else -> MediaRecorder.OutputFormat.AAC_ADTS
            }
            val encoder = when (outputFormat) {
                AMR -> MediaRecorder.AudioEncoder.AMR_NB
                else -> MediaRecorder.AudioEncoder.AAC
            }

            starTime = SystemClock.elapsedRealtime()
            isRecording.set(true)
            recorder.apply {
                reset()
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(format)
                setOutputFile(outputFile.absolutePath)
                setAudioEncoder(encoder)
                if (outputFormat == AAC) {
                    setAudioSamplingRate(22050)
                    setAudioEncodingBitRate(32000)
                }
                setOnInfoListener(listener)
                setOnErrorListener(errorListener)
                setMaxDuration(maxDuration)
                prepare()
                start()
            }
        }

        override fun stop(): Long {
            recorder.stop()
            isRecording.set(false)
            return SystemClock.elapsedRealtime() - starTime
        }

        override fun release() {
            recorder.release()
        }
    }
}
