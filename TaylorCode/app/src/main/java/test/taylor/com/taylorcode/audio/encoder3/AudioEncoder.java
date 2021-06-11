package test.taylor.com.taylorcode.audio.encoder3;

import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on 2018/5/14.
 */

public class AudioEncoder {
    ///////////////////AUDIO/////////////////////////////////
    // parameters for the encoder
    private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
    private MediaCodec aEncoder;                // API >= 16(Android4.1.2)
    private MediaCodec.BufferInfo aBufferInfo;        // API >= 16(Android4.1.2)
    private MediaCodecInfo audioCodecInfo;
    private MediaFormat audioFormat;
    private Thread audioEncoderThread;
    private volatile boolean audioEncoderLoop = false;
    private volatile boolean aEncoderEnd = false;
    private LinkedBlockingQueue<byte[]> audioQueue;
    private long presentationTimeUs;
    private final int TIMEOUT_USEC = 10000;
    private Callback mCallback;


    private static final String TAG = "AVEncoder";
    public static boolean DEBUG = true;
    /**
     * 设置回调
     *
     * @param callback 回调
     */
    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }


    public interface Callback {
        void outMediaFormat(final int trackIndex,MediaFormat mediaFormat);
        void outputAudioFrame(final int trackIndex,final ByteBuffer outBuf,final MediaCodec.BufferInfo bufferInfo);
    }

    public static AudioEncoder newInstance() {
        return new AudioEncoder();
    }

    private AudioEncoder() {

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initAudioEncoder(int sampleRate, int pcmFormat, int chanelCount){
        if (aEncoder != null) {
            return;
        }
        aBufferInfo = new MediaCodec.BufferInfo();
        audioQueue = new LinkedBlockingQueue<>();
        audioCodecInfo = selectCodec(AUDIO_MIME_TYPE);
        if (audioCodecInfo == null) {
            if (DEBUG) Log.e(TAG, "= =lgd= Unable to find an appropriate codec for " + AUDIO_MIME_TYPE);
            return;
        }
        Log.d(TAG, "===liuguodong===selected codec: " + audioCodecInfo.getName());
        audioFormat = MediaFormat.createAudioFormat(AUDIO_MIME_TYPE, sampleRate, chanelCount);
        audioFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
        audioFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_STEREO);//CHANNEL_IN_STEREO 立体声
        int bitRate = sampleRate * pcmFormat * chanelCount;
        audioFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate);
        audioFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, chanelCount);
        audioFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE, sampleRate);
        Log.d(TAG, " =lgd= =====format: " + audioFormat.toString());

        try {
            aEncoder = MediaCodec.createEncoderByType(AUDIO_MIME_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("===liuguodong===初始化音频编码器失败", e);
        }
        Log.d(TAG, String.format("= =lgd= =编码器:%s创建完成", aEncoder.getName()));
        // aEncoder.configure(audioFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private MediaCodecInfo selectCodec(String mimeType) {
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                continue;
            }
            String[] types = codecInfo.getSupportedTypes();
            for (int j = 0; j < types.length; j++) {
                if (types[j].equalsIgnoreCase(mimeType)) {
                    return codecInfo;
                }
            }
        }
        return null;
    }

    /**
     * 开始
     */
    public void start() {
        startAudioEncode();

    }

    /**
     * 停止
     */
    public void stop() {
        stopAudioEncode();

    }


    private void startAudioEncode() {
        if (aEncoder == null) {
            throw new RuntimeException(" =lgd= =请初始化音频编码器=====");
        }

        if (audioEncoderLoop) {
            throw new RuntimeException(" =lgd= 音频编码线程必须先停止===");
        }
        audioEncoderThread = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                Log.d(TAG, "===liuguodong=====Audio 编码线程 启动...");
                presentationTimeUs = System.currentTimeMillis() * 1000;
                aEncoderEnd = false;
                aEncoder.configure(audioFormat, null, null,
                        MediaCodec.CONFIGURE_FLAG_ENCODE);
                aEncoder.start();
                while (audioEncoderLoop && !Thread.interrupted()) {
                    try {
                        byte[] data = audioQueue.take();
                        if (DEBUG) Log.d(TAG, "== =lgd= 要编码的Audio数据大小:" + data.length);
                        encodeAudioData(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                if (aEncoder != null) {
                    //停止音频编码器
                    aEncoder.stop();
                    //释放音频编码器
                    aEncoder.release();
                    aEncoder = null;
                }
                audioQueue.clear();
                Log.d(TAG, "= =lgd= ==Audio 编码线程 退出...");
            }
        };
        audioEncoderLoop = true;
        audioEncoderThread.start();
    }

    private void stopAudioEncode() {
        Log.d(TAG, "== =lgd= ==stop Audio 编码...");
        aEncoderEnd = true;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void encodeAudioData(byte[] input){
        try {
            //拿到输入缓冲区,用于传送数据进行编码
            ByteBuffer[] inputBuffers = aEncoder.getInputBuffers();
            //得到当前有效的输入缓冲区的索引
            int inputBufferIndex = aEncoder.dequeueInputBuffer(TIMEOUT_USEC);
            if (inputBufferIndex >= 0) { //输入缓冲区有效
                if (DEBUG) Log.d(TAG, "== =lgd= Audio===inputBufferIndex: " + inputBufferIndex);
                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                inputBuffer.clear();
                //往输入缓冲区写入数据
                inputBuffer.put(input);

                //计算pts，这个值是一定要设置的
                /**
                 * the simplest way to set presentationTimeUs
                 */
                long pts = new Date().getTime() * 1000 - presentationTimeUs;
                if (aEncoderEnd) {
                    //结束时，发送结束标志，在编码完成后结束
                    Log.d(TAG, "=====liuguodong===send Audio Encoder BUFFER_FLAG_END_OF_STREAM====");
                    aEncoder.queueInputBuffer(inputBufferIndex, 0, input.length,
                            pts, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                } else {
                    //将缓冲区入队
                    aEncoder.queueInputBuffer(inputBufferIndex, 0, input.length,
                            pts, 0);
                }
            }

            //拿到输出缓冲区,用于取到编码后的数据
            ByteBuffer[] outputBuffers = aEncoder.getOutputBuffers();
            //拿到输出缓冲区的索引
            int outputBufferIndex = aEncoder.dequeueOutputBuffer(aBufferInfo, TIMEOUT_USEC);
            Log.d(TAG, "= =lgd= Audio======outputBufferIndex: "+outputBufferIndex);
            if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED){
                outputBuffers = aEncoder.getOutputBuffers();
            }else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED){
                Log.d(TAG, "= =lgd= ==Audio===INFO_OUTPUT_FORMAT_CHANGED===");
                //加入音轨的时刻,一定要等编码器设置编码格式完成后，再将它加入到混合器中，
                // 编码器编码格式设置完成的标志是dequeueOutputBuffer得到返回值为MediaCodec.INFO_OUTPUT_FORMAT_CHANGED
                final MediaFormat newformat = aEncoder.getOutputFormat(); // API >= 16
                if (null != mCallback && !aEncoderEnd) {
                    Log.d(TAG,"== =lgd= ==添加音轨 INFO_OUTPUT_FORMAT_CHANGED " + newformat.toString());
                    mCallback.outMediaFormat(1, newformat);
                }
            }
            while (outputBufferIndex >= 0) {
                //数据已经编码成AAC格式
                //outputBuffer保存的就是AAC数据
                ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                if (outputBuffer == null) {
                    throw new RuntimeException("encoderOutputBuffer " + outputBufferIndex +
                            " was null");
                }

                if ((aBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    // You shoud set output format to muxer here when you target Android4.3 or less
                    // but MediaCodec#getOutputFormat can not call here(because INFO_OUTPUT_FORMAT_CHANGED don't come yet)
                    // therefor we should expand and prepare output format from buffer data.
                    // This sample is for API>=18(>=Android 4.3), just ignore this flag here
                    Log.d(TAG, "== =lgd= Audio====drain:BUFFER_FLAG_CODEC_CONFIG===");
                    aBufferInfo.size = 0;
                }

                if (aBufferInfo.size != 0) {
                    // byte[] outData = new byte[mBufferInfo.size];
                    // outputBuffer.get(outData);
                    if (null != mCallback && !aEncoderEnd) {
                        mCallback.outputAudioFrame(1,outputBuffer, aBufferInfo);
                    }
                }
                //释放资源
                aEncoder.releaseOutputBuffer(outputBufferIndex, false);
                //拿到输出缓冲区的索引
                outputBufferIndex = aEncoder.dequeueOutputBuffer(aBufferInfo, 0);
                //编码结束的标志
                if ((aBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    Log.e(TAG, "= =lgd= =Recv Audio Encoder===BUFFER_FLAG_END_OF_STREAM=====");
                    audioEncoderLoop = false;
                    audioEncoderThread.interrupt();
                    return;
                }
            }
        } catch (Exception t) {
            Log.e(TAG, "= =lgd= =encodeAudioData=====error: " + t.toString());
        }
    }
}
