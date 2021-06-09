package test.taylor.com.taylorcode.audio

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.os.Process
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream


class AudioTrackManager {
    private var mAudioTrack: AudioTrack? = null
    private var mDis //播放文件的数据流
            : DataInputStream? = null
    private var mRecordThread: Thread? = null
    private var isStart = false

    //指定缓冲区大小。调用AudioRecord类的getMinBufferSize方法可以获得。
    private var mMinBufferSize = 0
    private fun initData() {
        //根据采样率，采样精度，单双声道来得到frame的大小。
        mMinBufferSize = AudioTrack.getMinBufferSize(mSampleRateInHz, mChannelConfig, mAudioFormat) //计算最小缓冲区
        //注意，按照数字音频的知识，这个算出来的是一秒钟buffer的大小。
        //创建AudioTrack
        mAudioTrack = AudioTrack(
            mStreamType, mSampleRateInHz, mChannelConfig,
            mAudioFormat, mMinBufferSize, mMode
        )
    }

    /**
     * 销毁线程方法
     */
    private fun destroyThread() {
        try {
            isStart = false
            if (null != mRecordThread && Thread.State.RUNNABLE == mRecordThread !!.state) {
                try {
                    Thread.sleep(500)
                    mRecordThread !!.interrupt()
                } catch (e: Exception) {
                    mRecordThread = null
                }
            }
            mRecordThread = null
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mRecordThread = null
        }
    }

    /**
     * 启动播放线程
     */
    private fun startThread() {
        destroyThread()
        isStart = true
        if (mRecordThread == null) {
            mRecordThread = Thread(recordRunnable)
            mRecordThread !!.start()
        }
    }

    /**
     * 播放线程
     */
    var recordRunnable = Runnable {
        try {
            //设置线程的优先级
            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO)
            val tempBuffer = ByteArray(mMinBufferSize)
            var readCount = 0
            while (mDis !!.available() > 0) {
                readCount = mDis !!.read(tempBuffer)
                if (readCount == AudioTrack.ERROR_INVALID_OPERATION || readCount == AudioTrack.ERROR_BAD_VALUE) {
                    continue
                }
                if (readCount != 0 && readCount != - 1) { //一边播放一边写入语音数据
                    //判断AudioTrack未初始化，停止播放的时候释放了，状态就为STATE_UNINITIALIZED
                    if (mAudioTrack !!.state == AudioTrack.STATE_UNINITIALIZED) {
                        initData()
                    }
                    mAudioTrack !!.play()
                    mAudioTrack !!.write(tempBuffer, 0, readCount)
                }
            }
            stopPlay() //播放完就停止播放
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 播放文件
     * @param path
     * @throws Exception
     */
    @Throws(Exception::class) private fun setPath(path: String) {
        val file = File(path)
        mDis = DataInputStream(FileInputStream(file))
    }

    /**
     * 启动播放
     *
     * @param path
     */
    fun startPlay(path: String) {
        try {
//            //AudioTrack未初始化
//            if(mAudioTrack.getState() == AudioTrack.STATE_UNINITIALIZED){
//                throw new RuntimeException("The AudioTrack is not uninitialized");
//            }//AudioRecord.getMinBufferSize的参数是否支持当前的硬件设备
//            else if (AudioTrack.ERROR_BAD_VALUE == mMinBufferSize || AudioTrack.ERROR == mMinBufferSize) {
//                throw new RuntimeException("AudioTrack Unable to getMinBufferSize");
//            }else{
            setPath(path)
            startThread()
            //            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 停止播放
     */
    fun stopPlay() {
        try {
            destroyThread() //销毁线程
            if (mAudioTrack != null) {
                if (mAudioTrack !!.state == AudioRecord.STATE_INITIALIZED) { //初始化成功
                    mAudioTrack !!.stop() //停止播放
                }
                if (mAudioTrack != null) {
                    mAudioTrack !!.release() //释放audioTrack资源
                }
            }
            if (mDis != null) {
                mDis !!.close() //关闭数据输入流
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        @Volatile
        private var mInstance: AudioTrackManager? = null

        //音频流类型
        private const val mStreamType = AudioManager.STREAM_MUSIC

        //指定采样率 （MediaRecoder 的采样率通常是8000Hz AAC的通常是44100Hz。 设置采样率为44100，目前为常用的采样率，官方文档表示这个值可以兼容所有的设置）
        private const val mSampleRateInHz = 44100

        //指定捕获音频的声道数目。在AudioFormat类中指定用于此的常量
        private const val mChannelConfig =  AudioFormat.CHANNEL_OUT_STEREO //单声道

        //指定音频量化位数 ,在AudioFormaat类中指定了以下各种可能的常量。通常我们选择ENCODING_PCM_16BIT和ENCODING_PCM_8BIT PCM代表的是脉冲编码调制，它实际上是原始音频样本。
        //因此可以设置每个样本的分辨率为16位或者8位，16位将占用更多的空间和处理能力,表示的音频也更加接近真实。
        private const val mAudioFormat = AudioFormat.ENCODING_PCM_16BIT

        //STREAM的意思是由用户在应用程序通过write方式把数据一次一次得写到audiotrack中。这个和我们在socket中发送数据一样，
        // 应用层从某个地方获取数据，例如通过编解码得到PCM数据，然后write到audiotrack。
        private const val mMode = AudioTrack.MODE_STREAM

        /**
         * 获取单例引用
         *
         * @return
         */
        val instance: AudioTrackManager?
            get() {
                if (mInstance == null) {
                    synchronized(AudioTrackManager::class.java) {
                        if (mInstance == null) {
                            mInstance = AudioTrackManager()
                        }
                    }
                }
                return mInstance
            }
    }

    init {
        initData()
    }
}