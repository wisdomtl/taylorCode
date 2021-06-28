package test.taylor.com.taylorcode.audio

import android.media.MediaCodec
import android.media.MediaCodec.*
import android.media.MediaCodecInfo
import android.media.MediaCodecInfo.CodecProfileLevel.AACObjectLC
import android.media.MediaCodecList
import android.media.MediaCodecList.REGULAR_CODECS
import android.media.MediaFormat
import android.media.MediaFormat.MIMETYPE_AUDIO_AAC
import android.util.Log
import test.taylor.com.taylorcode.audio.AudioManager.Companion.CHANNEL
import test.taylor.com.taylorcode.audio.AudioManager.Companion.SAMPLE_RATE
import test.taylor.com.taylorcode.audio.encoder2.AacPcmCoder
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object PcmEncoder {

    val BYTE_PER_AUDIO_SAMPLE = SAMPLE_RATE * CHANNEL * 16 / 8
    private const val TIMEOUT = 10_000L
    private const val ADTS_BIT_COUNT = 7

    fun toAac(pcmFile: File?, aacFile: File?) {
        //todo test file not exist
        val pcmInputStream = runCatching { FileInputStream(pcmFile) }.getOrNull() ?: return
        val aacOutputStream = runCatching { FileOutputStream(aacFile) }.getOrNull() ?: return
        val encoder = runCatching { createEncoder(CHANNEL, SAMPLE_RATE) }.getOrNull() ?: return
        val bufferInfo = MediaCodec.BufferInfo()

        var outputFinish = false
        var inputFinish = false
        var presentationTimeUs = 0L
        var totalReadByteCount = 0

        try {// 1. start encoding
            encoder.start()
            // keep looping until all audio data is encoded
            while (! outputFinish) {
                // 2. offer pcm data to encoder by buffer
                if (! inputFinish) {
                    encoder.dequeueInputBuffer(TIMEOUT).takeIf { it >= 0 }?.let { index ->
                        encoder.getInputBuffer(index)?.let { buffer ->
                            buffer.clear()
                            val bytes = ByteArray(buffer.limit())
                            val readByteCount = pcmInputStream.read(bytes)
                            inputFinish = readByteCount == - 1
                            if (inputFinish) {
                                encoder.queueInputBuffer(index, 0, 0, 0, BUFFER_FLAG_END_OF_STREAM)
                            } else {
                                buffer.put(bytes)
                                encoder.queueInputBuffer(index, 0, readByteCount, presentationTimeUs, 0)
                                totalReadByteCount += readByteCount
                                presentationTimeUs = ((totalReadByteCount.toFloat() / BYTE_PER_AUDIO_SAMPLE) * 1_000_000).toLong()
                            }
                        }
                    }
                }

                // 3. fetch encoded aac data from buffer
                encoder.dequeueOutputBuffer(bufferInfo, TIMEOUT).takeIf { it >= 0 }?.let { index ->
                    when {
                        bufferInfo.flags.and(BUFFER_FLAG_CODEC_CONFIG) != 0 -> { // bad buffer
                            encoder.releaseOutputBuffer(index, false)
                        }
                        bufferInfo.flags.and(BUFFER_FLAG_END_OF_STREAM) != 0 -> { // no more buffer
                            Log.v("ttaylor","toAac() end of loop")
                            outputFinish = true
                        }
                        bufferInfo.size != 0 -> {
                            encoder.getOutputBuffer(index)?.let { buffer -> // good buffer
                                buffer.position(bufferInfo.offset)
                                buffer.limit(bufferInfo.offset + bufferInfo.size)
                                val adtsPacketSize = bufferInfo.size + ADTS_BIT_COUNT
                                ByteArray(adtsPacketSize).addAdts(adtsPacketSize).also { aacByte ->
                                    buffer.get(aacByte, ADTS_BIT_COUNT, bufferInfo.size)
                                    aacOutputStream.write(aacByte)
                                }
                            }
                            encoder.releaseOutputBuffer(index, false)
                        }
                    }
                }
            }
        } catch (e: Exception) {
        } finally {
            pcmInputStream?.close()
            aacOutputStream?.close()
            encoder.release()
        }
    }

    private fun ByteArray.addAdts(packetSize: Int): ByteArray {
        val profile = 2 //AAC LC
        val freqIdx = 4 //44.1KHz
        val channel = 1 //CPE
        // fill in ADTS data
        this[0] = 0xFF.toByte()
        this[1] = 0xF9.toByte()
        this[2] = ((profile - 1 shl 6) + (freqIdx shl 2) + (channel shr 2)).toByte()
        this[3] = ((channel and 3 shl 6) + (packetSize shr 11)).toByte()
        this[4] = (packetSize and 0x7FF shr 3).toByte()
        this[5] = ((packetSize and 7 shl 5) + 0x1F).toByte()
        this[6] = 0xFC.toByte()
        return this
    }

private fun createEncoder(channel: Int, sampleRate: Int) = MediaFormat().let {
    it.setString(MediaFormat.KEY_MIME, MIMETYPE_AUDIO_AAC)
    it.setInteger(MediaFormat.KEY_BIT_RATE, 64000) // must be one of 64000 or 128000
    it.setInteger(MediaFormat.KEY_CHANNEL_COUNT, channel)
    it.setInteger(MediaFormat.KEY_SAMPLE_RATE, sampleRate)
    it.setInteger(MediaFormat.KEY_AAC_PROFILE, AACObjectLC)
    val encoderName = MediaCodecList(REGULAR_CODECS).findEncoderForFormat(it)
    createByCodecName(encoderName).apply { configure(it, null, null, CONFIGURE_FLAG_ENCODE) }
}
}