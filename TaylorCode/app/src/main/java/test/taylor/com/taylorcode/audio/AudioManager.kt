package test.taylor.com.taylorcode.audio

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaRecorder
import android.os.*
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class AudioManager(
    val context: Context,
    val type: String = AAC
) {

    companion object {
        const val AAC = "aac"
        const val AMR = "amr"
    }

    private val MSG_START_RECORD = 1
    private val MSG_STOP_RECORD = 2
    private val MSG_ERROR = 3

    private val RECORD_FAILED = 1
    private val RECORD_READY = 2
    private val RECORD_START = 3
    private val RECORD_SUCCESS = 4
    private val RECORD_CANCELED = 5

    var onRecordReady: (() -> Unit)? = null
    var onRecordStart: ((File) -> Unit)? = null
    var onRecordSuccess: ((File, Long) -> Unit)? = null
    var onRecordFail: (() -> Unit)? = null
    var onRecordCancel: (() -> Unit)? = null
    var onRecordReachedMaxTime: ((Int) -> Unit)? = null

    var maxDuration = 120 * 1000

    private var listener = object : AudioInfoListener {
        override fun onInfo(what: Int, extra: Int) {
            when (what) {
                MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED -> {
                    audioRecord.stop()
                    handler.post { isRecording.set(false) }
                    onRecordReachedMaxTime?.invoke(maxDuration)
                    onHandleEndRecord(true, maxDuration.toLong())// is this right
                }
                MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN, MediaRecorder.MEDIA_ERROR_SERVER_DIED -> {
                    error()
                }
            }
        }
    }
    private val audioRecord: AudioRecord = DefaultAudioRecord().apply { this.audioInfoListener = listener }

    private var audioFile: File? = null
    private var isRecording: AtomicBoolean = AtomicBoolean(false)
    private var cancelRecord: AtomicBoolean = AtomicBoolean(false)
    private val audioManager: AudioManager = context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val handlerThread: HandlerThread = HandlerThread("audio-recorder-thread").apply {
        start()
    }
    private val eventHandler = Handler(Looper.getMainLooper())
    private val handler: Handler = Handler(handlerThread.looper) { message ->
        when (message.what) {
            MSG_START_RECORD -> {
                startRecord()
            }
            MSG_STOP_RECORD -> {
                onCompleteRecord(message.obj as Boolean)
            }
            MSG_ERROR -> {
                onHandleEndRecord(message.obj as Boolean, message.arg1.toLong())
            }
        }
        false
    }

    private fun startRecord() {
        audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)

        if (isRecording.get()) {
            setState(RECORD_FAILED)
            return
        }

        if (getAudioFreeSpace() <= 0) {
            setState(RECORD_FAILED)
            return
        }

        audioFile = getAudioFile()

        audioFile?.let { file ->
            cancelRecord.set(false)
            try {
                if (!cancelRecord.get()) {
                    setState(RECORD_READY)
                    if (hasPermission()) {
                        audioRecord.start(type, file.absolutePath, maxDuration)
                        isRecording.set(true)
                        setState(RECORD_START)
                    } else {
                        onCompleteRecord(false)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onCompleteRecord(false)
            }
        } ?: kotlin.run {
            setState(RECORD_FAILED)
        }

        if (!isRecording.get()) {
            setState(RECORD_FAILED)
        }

    }

    private fun hasPermission(): Boolean {
        return context.checkCallingOrSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && context.checkCallingOrSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun onCompleteRecord(cancel: Boolean) {
        if (!isRecording.get()) {
            return
        }
        cancelRecord.set(cancel)
        audioManager.abandonAudioFocus(null)
        var duration = 0L
        try {
            duration = audioRecord.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            onHandleEndRecord(true, duration)
        }
    }

    private fun onHandleEndRecord(isSuccess: Boolean, duration: Long) {
        if (cancelRecord.get()) {
            audioFile?.deleteOnExit()
            setState(RECORD_CANCELED)
        } else if (!isSuccess) {
            audioFile?.deleteOnExit()
            setState(RECORD_FAILED)
        } else {
            if (isAudioFileInvalid()) {
                setState(RECORD_FAILED)
            } else {
                eventHandler.post {
                    audioFile?.let {
                        onRecordSuccess?.invoke(it, duration)
                    }
                }
            }
        }
        isRecording.set(false)
    }

    private fun isAudioFileInvalid() = audioFile == null || !audioFile!!.exists() || audioFile!!.length() <= 0

    fun start(maxDuration: Int = 120) {
        this.maxDuration = maxDuration * 1000
        handler.removeCallbacksAndMessages(null)
        handler.obtainMessage(MSG_START_RECORD).sendToTarget()
    }

    fun stop(cancel: Boolean = false) {
        handler.obtainMessage(MSG_STOP_RECORD).apply { obj = cancel }.sendToTarget()
    }

    fun release() {
        handler.removeCallbacksAndMessages(null)
        if (handlerThread.isAlive) {
            handlerThread.looper.quit()
        }

        audioRecord.release()
    }

    fun isRecording() = isRecording.get()

    private fun error() {
        handler.obtainMessage(MSG_ERROR).apply {
            obj = false
            arg1 = 0
        }.also { it.sendToTarget() }
    }

    private fun setState(state: Int) {
        eventHandler.post {
            when (state) {
                RECORD_FAILED -> onRecordFail?.invoke()
                RECORD_READY -> onRecordReady?.invoke()
                RECORD_START -> audioFile?.let { onRecordStart?.invoke(it) }
                RECORD_CANCELED -> onRecordCancel?.invoke()
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
     * the detail of how to record audio.
     */
    interface AudioRecord {
        /**
         * start audio recording
         */
        fun start(outputFormat: String, outputFile: String, maxDuration: Int)

        /**
         * stop audio recording
         * @return the duration of audio
         */
        fun stop(): Long

        fun release()
    }

    interface AudioInfoListener {
        fun onInfo(what: Int, extra: Int)
    }

    /**
     * record audio by [android.media.MediaRecorder]
     */
    class DefaultAudioRecord : AudioRecord {
        private var starTime: Long = 0L
        var audioInfoListener: AudioInfoListener? = null
        private val listener = MediaRecorder.OnInfoListener { mr, what, extra ->
            audioInfoListener?.onInfo(what, extra)
        }
        private val errorListener = MediaRecorder.OnErrorListener { mr, what, extra ->
            audioInfoListener?.onInfo(what, extra)
        }
        private val recorder = MediaRecorder()

        @Synchronized
        override fun start(outputFormat: String, outputFile: String, maxDuration: Int) {
            val format = when (outputFormat) {
                AMR -> MediaRecorder.OutputFormat.AMR_NB
                else -> MediaRecorder.OutputFormat.AAC_ADTS
            }
            val encoder = when (outputFormat) {
                AMR -> MediaRecorder.AudioEncoder.AMR_NB
                else -> MediaRecorder.AudioEncoder.AAC
            }

            starTime = System.currentTimeMillis()
            recorder.apply {
                reset()
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(format)
                setOutputFile(outputFile)
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

        @Synchronized
        override fun stop(): Long {
            recorder.apply {
                stop()
            }
            return System.currentTimeMillis() - starTime
        }

        override fun release() {
            recorder.release()
        }
    }
}
