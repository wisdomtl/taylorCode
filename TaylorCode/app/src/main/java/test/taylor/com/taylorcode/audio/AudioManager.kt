package test.taylor.com.taylorcode.audio

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioFormat.CHANNEL_IN_MONO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioManager
import android.media.AudioRecord
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
import java.util.concurrent.atomic.AtomicLong

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

        const val SOURCE = MediaRecorder.AudioSource.MIC
        const val SAMPLE_RATE = 44100
        const val CHANNEL = 1
    }

    private val STATE_FAILED = 1
    private val STATE_READY = 2
    private val STATE_START = 3
    private val STATE_SUCCESS = 4
    private val STATE_CANCELED = 5
    private val STATE_REACH_MAX_TIME = 6

    /**
     * the callback business layer cares about
     */
    var onRecordReady: (() -> Unit)? = null
    var onRecordStart: ((File) -> Unit)? = null
    var onRecordSuccess: ((File, Long) -> Unit)? = null// deliver audio file and duration to business layer
    var onRecordFail: (() -> Unit)? = null
    var onRecordCancel: (() -> Unit)? = null
    var onRecordReachedMaxTime: ((Int) -> Unit)? = null

    /**
     * deliver recording state to business layer
     */
    private val callbackHandler = Handler(Looper.getMainLooper())

    private var maxDuration = 120 * 1000
    private var recorder: Recorder = if (type == PCM) AudioRecorder(type) else MediaRecord(type)
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
        recorder.release()
    }

    fun isRecording() = recorder.isRecording()

    private fun startRecord() {
        audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)

        if (recorder.isRecording()) {
            setState(STATE_FAILED)
            return
        }

        if (getFreeSpace() <= 0) {
            setState(STATE_FAILED)
            return
        }

        audioFile = getAudioFile()
        if (audioFile == null) setState(STATE_FAILED)

        cancelRecord.set(false)
        try {
            if (! cancelRecord.get()) {
                setState(STATE_READY)
                if (hasPermission()) {
                    launch { recorder.start(audioFile !!, maxDuration) }
                    setState(STATE_START)
                } else {
                    stopRecord(false)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stopRecord(false)
        }

        if (! recorder.isRecording()) {
            setState(STATE_FAILED)
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
        try {
            recorder.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            handleRecordEnd(isSuccess = true, isReachMaxTime = false)
        }
    }

    private fun handleRecordEnd(isSuccess: Boolean, isReachMaxTime: Boolean) {
        if (cancelRecord.get()) {
            audioFile?.deleteOnExit()
            setState(STATE_CANCELED)
        } else if (! isSuccess) {
            audioFile?.deleteOnExit()
            setState(STATE_FAILED)
        } else {
            if (isAudioFileInvalid()) {
                setState(STATE_FAILED)
                if (isReachMaxTime) {
                    setState(STATE_REACH_MAX_TIME)
                }
            } else {
                setState(STATE_SUCCESS)
            }
        }
    }

    private fun isAudioFileInvalid() = audioFile == null || ! audioFile !!.exists() || audioFile !!.length() <= 0

    /**
     * change recording state and invoke according callback to main thread
     */
    private fun setState(state: Int) {
        callbackHandler.post {
            when (state) {
                STATE_FAILED -> onRecordFail?.invoke()
                STATE_READY -> onRecordReady?.invoke()
                STATE_START -> audioFile?.let { onRecordStart?.invoke(it) }
                STATE_CANCELED -> onRecordCancel?.invoke()
                STATE_SUCCESS -> audioFile?.let { onRecordSuccess?.invoke(it, recorder.getDuration()) }
                STATE_REACH_MAX_TIME -> onRecordReachedMaxTime?.invoke(maxDuration)
            }
        }
    }

    private fun getFreeSpace(): Long {
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
         * whether audio is recording
         */
        fun isRecording(): Boolean

        /**
         * the length of audio
         */
        fun getDuration(): Long

        /**
         * start audio recording, it is time-consuming
         */
        fun start(outputFile: File, maxDuration: Int)

        /**
         * stop audio recording
         */
        fun stop()

        /**
         * release the resource of audio recording
         */
        fun release()
    }

    /**
     * record audio by [android.media.MediaRecorder]
     */
    inner class MediaRecord(override var outputFormat: String) : Recorder {
        private var starTime = AtomicLong()
        private val listener = MediaRecorder.OnInfoListener { _, what, _ ->
            when (what) {
                MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED -> {
                    stop()
                    handleRecordEnd(isSuccess = true, isReachMaxTime = true)
                }
                else -> {
                    handleRecordEnd(isSuccess = false, isReachMaxTime = false)
                }
            }
        }
        private val errorListener = MediaRecorder.OnErrorListener { _, _, _ ->
            handleRecordEnd(isSuccess = false, isReachMaxTime = false)
        }
        private val recorder = MediaRecorder()
        private var isRecording = AtomicBoolean(false)
        private var duration = 0L

        override fun isRecording(): Boolean = isRecording.get()

        override fun getDuration(): Long = duration

        override fun start(outputFile: File, maxDuration: Int) {
            val format = when (outputFormat) {
                AMR -> MediaRecorder.OutputFormat.AMR_NB
                else -> MediaRecorder.OutputFormat.AAC_ADTS
            }
            val encoder = when (outputFormat) {
                AMR -> MediaRecorder.AudioEncoder.AMR_NB
                else -> MediaRecorder.AudioEncoder.AAC
            }

            starTime.set(SystemClock.elapsedRealtime())
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

        override fun stop() {
            recorder.stop()
            isRecording.set(false)
            duration = SystemClock.elapsedRealtime() - starTime.get()
        }

        override fun release() {
            recorder.release()
        }
    }

    /**
     * record audio by [android.media.AudioRecord]
     */
    inner class AudioRecorder(override var outputFormat: String) : Recorder {
        private var bufferSize: Int = 0
        private var isRecording = AtomicBoolean(false)
        private var startTime = AtomicLong()
        private var duration = 0L
        private val audioRecord by lazy {
            bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_IN_MONO, ENCODING_PCM_16BIT)
            AudioRecord(SOURCE, SAMPLE_RATE, CHANNEL_IN_MONO, ENCODING_PCM_16BIT, bufferSize)
        }

        override fun isRecording(): Boolean = isRecording.get()

        override fun getDuration(): Long = duration

        override fun start(outputFile: File, maxDuration: Int) {
            if (audioRecord.state == AudioRecord.STATE_UNINITIALIZED) return

            isRecording.set(true)
            startTime.set(SystemClock.elapsedRealtime())
            outputFile.outputStream().use { outputStream ->
                audioRecord.startRecording()
                val audioData = ByteArray(bufferSize)
                while (continueRecord(maxDuration)) {
                    audioRecord.read(audioData, 0, audioData.size)
                    outputStream.write(audioData)
                }
                if (audioRecord.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                    audioRecord.stop()
                }
                if (duration >= maxDuration) handleRecordEnd(isSuccess = true, isReachMaxTime = true)
            }
        }

        private fun continueRecord(maxDuration: Int): Boolean {
            duration = SystemClock.elapsedRealtime() - startTime.get()
            return isRecording.get() && audioRecord.recordingState == AudioRecord.RECORDSTATE_RECORDING && duration < maxDuration
        }

        override fun stop() {
            isRecording.set(false)
        }

        override fun release() {
            audioRecord.release()
        }
    }
}
