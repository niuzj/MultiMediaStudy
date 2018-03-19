package niu.multimediastudy.chapter6;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.util.FileUtil;

/**
 * Created by Qinlian_niu on 2018/3/16.
 */

public class Section3Activity extends SectionBaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    private AudioRecord mAudioRecord;
    private AudioTrack mAudioTrack;
    private File outputFile;
    private DataOutputStream mDataOutputStream;
    private DataInputStream mDataInputStream;
    private byte[] buffer;
    private SaveFileThread mSaveFileThread;
    private PlayFileThread mPlayFileThread;
    private boolean isRecording;
    private boolean isPlaying;

    @Override
    public void addItems() {
        listItems = new String[]{"AudioRecord配置", "开始录制", "结束录制",
                "AudioTrack配置", "AudioTrack播放", "AudioTrack结束播放"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "AudioRecord配置":
                if (mAudioRecord == null) {
                    mSaveFileThread = new SaveFileThread();
                    int audioSource = MediaRecorder.AudioSource.MIC;
                    //采样率
                    int sampleRateInHz = 11025;
                    //声道数
                    int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
                    //音频格式
                    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
                    //缓冲区大小
                    int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
                    buffer = new byte[bufferSizeInBytes / 2];
                    mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);

                    //输出目录
                    FileUtil fileUtil = new FileUtil();
                    String rootPath = fileUtil.getRootPath();
                    try {
                        outputFile = File.createTempFile(System.currentTimeMillis() + "", ".pcm", new File(rootPath));

                        mDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case "开始录制":
                if (mAudioRecord != null) {
                    mAudioRecord.startRecording();
                    isRecording = true;
                    mSaveFileThread = new SaveFileThread();
                    mSaveFileThread.start();
                }
                break;

            case "结束录制":
                if (mAudioRecord != null) {
                    mAudioRecord.stop();
                    isRecording = false;
                }
                break;

            case "AudioTrack配置":
                if (mAudioTrack == null && outputFile != null) {
                    mPlayFileThread = new PlayFileThread();
                    int streamType = AudioManager.STREAM_MUSIC;
                    int sampleRateInHz = 11025;
                    int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
                    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
                    int bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
                    buffer = new byte[bufferSizeInBytes / 2];
                    mAudioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes, AudioTrack.MODE_STREAM);

                    try {
                        mDataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(outputFile)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case "AudioTrack播放":
                if (mAudioTrack != null && !isPlaying) {
                    isPlaying = true;
                    mPlayFileThread = new PlayFileThread();
                    mPlayFileThread.start();
                    mAudioTrack.play();
                }
                break;

            case "AudioTrack结束播放":
                if (mAudioTrack != null) {
                    mAudioTrack.stop();
                    isPlaying = false;
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            isRecording = false;
        }

        if (mAudioTrack != null) {
            mAudioTrack.stop();
            isPlaying = false;
        }

    }

    private class SaveFileThread extends Thread {
        @Override
        public void run() {
            while (isRecording) {
                mAudioRecord.read(buffer, 0, buffer.length);
                try {
                    mDataOutputStream.write(buffer, 0, buffer.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                mDataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class PlayFileThread extends Thread {
        @Override
        public void run() {
            try {
                while (isPlaying && mDataInputStream.available() > 0) {
                    int i = 0;
                    while (i < buffer.length && mDataInputStream.available() > 0) {
                        buffer[i] = mDataInputStream.readByte();
                        i++;
                    }
                    mAudioTrack.write(buffer, 0, buffer.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    mDataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
