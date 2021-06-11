package test.taylor.com.taylorcode.audio.encoder2;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * AAC 编解码
 */
public class AacPcmCoder {
    private static final String TAG = "AacPcmCoder";
    private final static String AUDIO_MIME = "audio/mp4a-latm";
    private final static long AUDIO_BYTES_PER_SAMPLE = 44100 * 1 * 16 / 8;

    /**
     * PCM 编码为 AAC 格式
     *
     * @param inPcmFile
     * @param outAacFile
     * @throws IOException
     */
    public static void encodePcmToAac(File inPcmFile, File outAacFile) throws IOException {
        FileInputStream fisRawAudio = null;
        FileOutputStream fosAccAudio = null;
        long startTime = System.currentTimeMillis() * 1000;
        MediaCodec audioEncoder = createAudioEncoder();

        try {
            fisRawAudio = new FileInputStream(inPcmFile);
            fosAccAudio = new FileOutputStream(outAacFile);
            // 开始编码
            audioEncoder.start();
            ByteBuffer[] audioInputBuffers = audioEncoder.getInputBuffers();
            ByteBuffer[] audioOutputBuffers = audioEncoder.getOutputBuffers();
            boolean sawInputEOS = false;
            boolean sawOutputEOS = false;
            long audioTimeUs = 0;
            MediaCodec.BufferInfo outBufferInfo = new MediaCodec.BufferInfo();
            boolean readRawAudioEOS = false;
            byte[] rawInputBytes = new byte[8192];
            int readRawAudioCount;
            int rawAudioSize = 0;
            long lastAudioPresentationTimeUs = 0;
            int inputBufIndex, outputBufIndex;
            while (!sawOutputEOS) {
                /**
                 * 输入
                 */
                if (!sawInputEOS) { // 还没输入完
                    inputBufIndex = audioEncoder.dequeueInputBuffer(10_000);
                    if (inputBufIndex >= 0) {
                        /**
                         * 获取 buffer 并根据它大小新建 byte 数组
                         */
                        ByteBuffer inputBuffer = audioInputBuffers[inputBufIndex];
                        inputBuffer.clear();
                        int bufferSize = inputBuffer.remaining();
                        if (bufferSize != rawInputBytes.length) {
                            rawInputBytes = new byte[bufferSize];
                        }

                        /**
                         * 从pcm文件读数据到byte数组
                         */
                        readRawAudioCount = fisRawAudio.read(rawInputBytes);
                        // pcm文件读完了
                        if (readRawAudioCount == -1) {
                            readRawAudioEOS = true;
                        }

                        /**
                         * 将 byte 数组写入 buffer 再将 buffer 数据写入 codec
                         */
                        // pcm文件读完后告诉codec input完毕
                        if (readRawAudioEOS) {
                            audioEncoder.queueInputBuffer(inputBufIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                            sawInputEOS = true;
                        }
                        // 将读取的pcm文件 写入到input buffer 中并将buffer交给 codec 处理
                        else {
                            inputBuffer.put(rawInputBytes, 0, readRawAudioCount);
                            rawAudioSize += readRawAudioCount;
//                            audioTimeUs = System.currentTimeMillis() * 1000 - startTime;
                            audioEncoder.queueInputBuffer(inputBufIndex, 0, readRawAudioCount, audioTimeUs, 0);
                            audioTimeUs = (long) (1_000_000 * ((float) rawAudioSize / AUDIO_BYTES_PER_SAMPLE)); //计算当前读取的字节数量是多少帧的数量
                        }
                    }
                }

                /**
                 * 输出编码后的内容到outBufferInfo
                 */
                outputBufIndex = audioEncoder.dequeueOutputBuffer(outBufferInfo, 10_000);
                if (outputBufIndex >= 0) {
                    // 将不合格的buffer 直接回收
                    if ((outBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        Log.i(TAG, "audio encoder: codec config buffer");
                        audioEncoder.releaseOutputBuffer(outputBufIndex, false);
                        continue;
                    }
                    // 读到编码后的内容
                    if (outBufferInfo.size != 0) {
                        ByteBuffer outBuffer = audioOutputBuffers[outputBufIndex];
                        // out buffer 定位
                        outBuffer.position(outBufferInfo.offset);
                        outBuffer.limit(outBufferInfo.offset + outBufferInfo.size);
                        if (lastAudioPresentationTimeUs <= outBufferInfo.presentationTimeUs) { // 有新数据
                            lastAudioPresentationTimeUs = outBufferInfo.presentationTimeUs;
                            int outBufSize = outBufferInfo.size;
                            int outPacketSize = outBufSize + 7;
                            outBuffer.position(outBufferInfo.offset);
                            outBuffer.limit(outBufferInfo.offset + outBufSize);
                            byte[] outData = new byte[outPacketSize];
                            addADTStoPacket(outData, outPacketSize);// 添加 adts
                            outBuffer.get(outData, 7, outBufSize);
                            fosAccAudio.write(outData, 0, outData.length);//形成 aac文件
                            //Log.v(TAG, outData.length + " bytes written.");
                        } else {
                            Log.e(TAG, "error sample! its presentationTimeUs should not lower than before.");
                        }
                    }
                    //释放 buffer 归还给 codec
                    audioEncoder.releaseOutputBuffer(outputBufIndex, false);
                    // 判断是不是读完了
                    if ((outBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        sawOutputEOS = true;
                    }
                } else if (outputBufIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    audioOutputBuffers = audioEncoder.getOutputBuffers();
                } else if (outputBufIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat audioFormat = audioEncoder.getOutputFormat();
                    Log.i(TAG, "format change : " + audioFormat);
                }
            }
        } finally {
            Log.i(TAG, "encodePcmToAac: finish");
            if (fisRawAudio != null) {
                fisRawAudio.close();
            }
            if (fosAccAudio != null) {
                fosAccAudio.close();
            }
            audioEncoder.release();
        }
    }

    /**
     * AAC 解码至 PCM 格式
     *
     * @param aacFile
     * @param pcmFile
     * @throws IOException
     */
    public static void decodeAacTomPcm(File aacFile, File pcmFile) throws IOException {
        MediaExtractor extractor = new MediaExtractor();
        extractor.setDataSource(aacFile.getAbsolutePath());
        MediaFormat mediaFormat = null;
        for (int i = 0; i < extractor.getTrackCount(); i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("audio/")) {
                extractor.selectTrack(i);
                mediaFormat = format;
                break;
            }
        }
        if (mediaFormat == null) {
            Log.e(TAG, "Invalid file with audio track.");
            extractor.release();
            return;
        }

        FileOutputStream fosDecoder = new FileOutputStream(pcmFile);
        String mediaMime = mediaFormat.getString(MediaFormat.KEY_MIME);
        Log.i(TAG, "decodeAacToPcm: mimeType: " + mediaMime);
        MediaCodec codec = MediaCodec.createDecoderByType(mediaMime);
        codec.configure(mediaFormat, null, null, 0);
        codec.start();
        ByteBuffer[] codecInputBuffers = codec.getInputBuffers();
        ByteBuffer[] codecOutputBuffers = codec.getOutputBuffers();
        final long kTimeOutUs = 10_000;
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        boolean sawInputEOS = false;
        boolean sawOutputEOS = false;

        try {
            while (!sawOutputEOS) {
                if (!sawInputEOS) {
                    int inputBufIndex = codec.dequeueInputBuffer(kTimeOutUs);
                    if (inputBufIndex >= 0) {
                        ByteBuffer dstBuf = codecInputBuffers[inputBufIndex];
                        int sampleSize = extractor.readSampleData(dstBuf, 0);
                        if (sampleSize < 0) {
                            Log.i(TAG, "saw input EOS.");
                            sawInputEOS = true;
                            codec.queueInputBuffer(inputBufIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        } else {
                            codec.queueInputBuffer(inputBufIndex, 0, sampleSize, extractor.getSampleTime(), 0);
                            extractor.advance();
                        }
                    }
                }

                int outputBufferIndex = codec.dequeueOutputBuffer(info, kTimeOutUs);
                if (outputBufferIndex >= 0) {
                    // Simply ignore codec config buffers.
                    if ((info.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        Log.i(TAG, "audio encoder: codec config buffer");
                        codec.releaseOutputBuffer(outputBufferIndex, false);
                        continue;
                    }

                    if (info.size != 0) {
                        ByteBuffer outBuf = codecOutputBuffers[outputBufferIndex];
                        outBuf.position(info.offset);
                        outBuf.limit(info.offset + info.size);
                        byte[] data = new byte[info.size];
                        outBuf.get(data);
                        fosDecoder.write(data);
                    }

                    codec.releaseOutputBuffer(outputBufferIndex, false);
                    if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        Log.i(TAG, "saw output EOS.");
                        sawOutputEOS = true;
                    }
                } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    codecOutputBuffers = codec.getOutputBuffers();
                    Log.i(TAG, "output buffers have changed.");
                } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat oformat = codec.getOutputFormat();
                    Log.i(TAG, "output format has changed to " + oformat);
                }
            }
        } finally {
            Log.i(TAG, "decodeAacToPcm finish");
            codec.stop();
            codec.release();
            extractor.release();
            fosDecoder.close();
        }
    }

    /**
     * 创建编码器
     *
     * @return
     * @throws IOException
     */
    private static MediaCodec createAudioEncoder() throws IOException {
        MediaCodec codec = MediaCodec.createEncoderByType(AUDIO_MIME);
        MediaFormat format = new MediaFormat();
        format.setString(MediaFormat.KEY_MIME, AUDIO_MIME);
        format.setInteger(MediaFormat.KEY_BIT_RATE, 64000);
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, 44100);
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
        codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        return codec;
    }

    /**
     * 添加ADTS (固定的7字节内容)
     *
     * @param packet
     * @param packetLen
     */
    private static void addADTStoPacket(byte[] packet, int packetLen) {
        int profile = 2;  //AAC LC
        int freqIdx = 4;  //44.1KHz
        int chanCfg = 1;  //CPE
        // fill in ADTS data
        packet[0] = (byte) 0xFF;
        packet[1] = (byte) 0xF9;
        packet[2] = (byte) (((profile - 1) << 6) + (freqIdx << 2) + (chanCfg >> 2));
        packet[3] = (byte) (((chanCfg & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;
    }

}
