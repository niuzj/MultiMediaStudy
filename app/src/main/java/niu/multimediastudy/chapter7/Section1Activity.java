package niu.multimediastudy.chapter7;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.widget.Toast;

import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/19.
 */

public class Section1Activity extends SectionBaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    private AudioTrack mAudioTrack;
    private short[] audioData;
    private PlayThread mPlayThread;
    private PlaySinThread mPlaySinThread;
    private boolean isPlaying;

    //每秒采用多少数据作为音频样本
    private int sampleRateInHz = 11025;
    private int hz = 440;
    private float perAngle;

    @Override
    public void addItems() {
        listItems = new String[]{"配置AudioTrack", "播放合成声音", "播放正弦声音", "暂停播放", "HZ+", "HZ-"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "配置AudioTrack":
                if (mAudioTrack == null) {
                    int streamType = AudioManager.STREAM_MUSIC;
                    //每秒采集多少数据作为音频样本
                    int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
                    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
                    int bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
                    mAudioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes, AudioTrack.MODE_STREAM);
                }
                break;

            case "播放合成声音":
                audioData = new short[]{
                        8130, 15752, 22389, 27625, 31134, 32695, 32210, 29700,
                        25354, 19410, 12253, 4329, -3865, -11818, -19032, -25055,
                        -29511, -32121, -32722, -31276, -27874, -22728, -16160,
                        -8582, -466
                };
                if (mAudioTrack != null) {
                    isPlaying = true;
                    mPlayThread = new PlayThread();
                    mPlayThread.start();
                    mAudioTrack.play();
                } else {
                    Toast.makeText(this, "配置AudioTrack", Toast.LENGTH_SHORT).show();
                }
                break;

            case "播放正弦声音":
                playSin();
                break;

            case "暂停播放":
                stopPlay();
                break;

            case "HZ+":
                stopPlay();
                hz += 200;
                Log.e(TAG, "当前频率为" + hz + "HZ");
                playSin();
                break;

            case "HZ-":
                stopPlay();
                hz -= 200;
                Log.e(TAG, "当前频率为" + hz + "HZ");
                playSin();
                break;
        }
    }

    private void stopPlay() {
        if (mAudioTrack != null && isPlaying) {
            isPlaying = false;
            mAudioTrack.stop();
        }
    }

    /**
     * 播放正弦音频
     */
    private void playSin() {
        perAngle = (float) (2 * Math.PI * hz / sampleRateInHz);
        if (mAudioTrack != null) {
            isPlaying = true;
            mPlaySinThread = new PlaySinThread();
            mPlaySinThread.start();
            mAudioTrack.play();
        } else {
            Toast.makeText(this, "请先配置AudioTrack", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAudioTrack != null) {
            isPlaying = false;
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }

    private class PlayThread extends Thread {
        @Override
        public void run() {
            if (audioData != null && audioData.length > 0) {
                while (isPlaying) {
                    mAudioTrack.write(audioData, 0, audioData.length);
                }
            }
        }
    }

    private class PlaySinThread extends Thread {
        @Override
        public void run() {
            while (isPlaying) {
                audioData = new short[sampleRateInHz];
                float angle = 0;
                for (int i = 0; i < sampleRateInHz; i++) {
                    audioData[i] = (short) (Short.MAX_VALUE * Math.sin(angle));
                    angle += perAngle;
                }
                mAudioTrack.write(audioData, 0, audioData.length);
            }
        }
    }


}
