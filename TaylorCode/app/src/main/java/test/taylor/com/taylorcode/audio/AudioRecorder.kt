package test.taylor.com.taylorcode.audio

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.SystemClock
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class AudioRecorder(override var infoListener: AudioManager.InfoListener, override var outputFormat: String) : AudioManager.Recorder {

    private val SOURCE = MediaRecorder.AudioSource.MIC
    private val SAMPLE_RATE = 44100
    private val CHANNEL = AudioFormat.CHANNEL_IN_MONO

    private var bufferSize: Int = 0
    private var isRecording: AtomicBoolean = AtomicBoolean(false)
    private var startTime: Long = 0L

    val audioRecord by lazy {
        val format = when (outputFormat) {
            AudioManager.PCM -> AudioFormat.ENCODING_PCM_16BIT
            else -> AudioFormat.ENCODING_AAC_HE_V1
        }
        //todo: handle ERROR_BAD_VALUE for bufferSize
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL, format)
        AudioRecord(SOURCE, SAMPLE_RATE, CHANNEL, format, bufferSize)
    }

    override fun isRecording(): Boolean = isRecording.get()

    override suspend fun start(outputFile: File, maxDuration: Int) {
        if (audioRecord.state == AudioRecord.STATE_UNINITIALIZED) return

        isRecording.set(true)
        startTime = SystemClock.elapsedRealtime()
        outputFile.outputStream().use { outputStream ->
            audioRecord.startRecording()
            val audioData = ByteArray(bufferSize)
            while (isRecording.get() && audioRecord.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                audioRecord.read(audioData, 0, audioData.size)
                outputStream.write(audioData)
            }
        }
    }

    override fun stop(): Long {
        audioRecord.stop()
        isRecording.set(false)
        return SystemClock.elapsedRealtime() - startTime
    }

    override fun release() {
        audioRecord.release()
    }
}