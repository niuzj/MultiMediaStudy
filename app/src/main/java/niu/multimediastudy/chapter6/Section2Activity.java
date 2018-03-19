package niu.multimediastudy.chapter6;

import android.content.ContentValues;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.util.FileUtil;

/**
 * Created by Qinlian_niu on 2018/3/16.
 */

public class Section2Activity extends SectionBaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    private MediaRecorder mMediaRecorder;
    private File outputFile;
    private boolean isRecording = false;
    private VolumnThread mVolumnThread;

    @Override
    public void addItems() {
        listItems = new String[]{"设置MediaRecorder", "开始录制音频", "结束录制音频", "将录制的音频插入MediaStore"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "设置MediaRecorder":
                if (mMediaRecorder == null) {
                    mVolumnThread = new VolumnThread();
                    mMediaRecorder = new MediaRecorder();
                    //如果不设置该方法，输出文件里不包含音轨
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //设置音频的输出格式
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //设置音频编解码器
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //设置输出目录
                    FileUtil fileUtil = new FileUtil();
                    String rootPath = fileUtil.getRootPath();
                    try {
                        outputFile = File.createTempFile(System.currentTimeMillis() + "", ".mp3", new File(rootPath));
                        mMediaRecorder.setOutputFile(outputFile.getAbsolutePath());
                        mMediaRecorder.prepare();
                        Log.e(TAG, "prepare()");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //在prepare()之前可以设置的
                //设置允许录制的音轨数量
                //mMediaRecorder.setAudioChannels(1);
                //设置比特率
                //mMediaRecorder.setAudioEncodingBitRate(84000);
                //设置采样率
                //mMediaRecorder.setAudioSamplingRate(84000);
                break;

            case "开始录制音频":
                if (mMediaRecorder != null) {
                    mMediaRecorder.start();
                    isRecording = true;
                    mVolumnThread.start();
                    Log.e(TAG, "start()");
                }
                break;

            case "结束录制音频":
                if (mMediaRecorder != null) {
                    mMediaRecorder.stop();
                    mMediaRecorder.release();
                    mMediaRecorder = null;
                    isRecording = false;
                    mVolumnThread = null;
                    Log.e(TAG, "stop()");
                }
                break;

            case "将录制的音频插入MediaStore":
                if (outputFile != null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.Audio.Media.DATA, outputFile.getAbsolutePath());
                    contentValues.put(MediaStore.Audio.Media.DATE_ADDED, System.currentTimeMillis());
                    contentValues.put(MediaStore.Audio.Media.TITLE, "Section2Activity");
                    getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues);
                    outputFile = null;
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            isRecording = false;
            mVolumnThread = null;
        }
    }

    private class VolumnThread extends Thread {
        @Override
        public void run() {
            while (isRecording) {
                int maxAmplitude = mMediaRecorder.getMaxAmplitude();
                Log.e(TAG, "maxAmplitude == " + maxAmplitude);
                try {
                    Thread.currentThread().sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
