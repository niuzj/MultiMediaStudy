package niu.multimediastudy.chapter5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import niu.multimediastudy.R;
import niu.multimediastudy.util.FileUtil;

public class BgAudioService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    private MediaPlayer mMediaPlayer = null;

    public BgAudioService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand:" + startId);
        FileUtil fileUtil = new FileUtil();
        mMediaPlayer = MediaPlayer.create(this, fileUtil.getUriFromRaw(R.raw.kiss, this));
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
