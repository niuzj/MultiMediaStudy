package niu.multimediastudy.chapter7;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.fftpack.RealDoubleFFT;
import niu.multimediastudy.view.C7S2View;

/**
 * Created by Qinlian_niu on 2018/3/19.
 */

public class Section2Activity extends SectionBaseActivity {

    private AudioRecord mAudioRecord;
    private RealDoubleFFT mRealDoubleFFT;
    private int blockSize = 256;
    private ProcessAsyncTask mProcessAsyncTask;
    private boolean isRecording;
    private C7S2View mC7S2View;

    private final int CHECK_RECORD = 1;


    @Override
    public void addItems() {
        listItems = new String[]{"初始配置", "开始录制", "结束录制"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "初始配置":
                if (mAudioRecord == null) {

                    mRealDoubleFFT = new RealDoubleFFT(blockSize);

                    int audioResource = MediaRecorder.AudioSource.MIC;
                    int sampleRateInHz = 8000;
                    int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
                    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
                    int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);

                    mAudioRecord = new AudioRecord(audioResource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);

                    mC7S2View = new C7S2View(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    mC7S2View.setLayoutParams(layoutParams);
                    mBinding.linearlayout.addView(mC7S2View);

                }
                break;

            case "开始录制":
                if (mAudioRecord != null && checkPermission(Manifest.permission.RECORD_AUDIO, CHECK_RECORD)) {
                    startRecord();
                }
                break;

            case "结束录制":
                if (mAudioRecord != null && isRecording) {
                    mAudioRecord.stop();
                    isRecording = false;
                }
                break;
        }
    }

    private void startRecord() {
        mProcessAsyncTask = new ProcessAsyncTask();
        mAudioRecord.startRecording();
        isRecording = true;
        mProcessAsyncTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAudioRecord != null) {
            isRecording = false;
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CHECK_RECORD:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startRecord();
                } else {
                    finish();
                }
                break;
        }
    }

    private class ProcessAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            short[] buffer = new short[blockSize];
            double[] toTransform = new double[blockSize];
            long oldTime = 0;
            long currentTime;
            while (isRecording) {
                int read = mAudioRecord.read(buffer, 0, blockSize);
                currentTime = System.currentTimeMillis();
                if (currentTime - oldTime > 50) {
                    for (int i = 0; i < read; i++) {
                        //期望值应该在-1.0～1.0之间
                        toTransform[i] = buffer[i] / Short.MAX_VALUE;
                    }
                    mRealDoubleFFT.ft(toTransform);
                    publishProgress(toTransform);
                    oldTime = currentTime;
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            double[] value = (double[]) values[0];
            for (int i = 0; i < value.length; i++) {
                if (isRecording && value[i] != 0) {
                    mC7S2View.setHeight((value[i]));
                }
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

}
