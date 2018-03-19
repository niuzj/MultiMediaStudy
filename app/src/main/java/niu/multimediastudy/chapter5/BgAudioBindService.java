package niu.multimediastudy.chapter5;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import niu.multimediastudy.R;
import niu.multimediastudy.util.FileUtil;

public class BgAudioBindService extends Service {

    private final String TAG = this.getClass().getSimpleName();
    private MediaPlayer mMediaPlayer;

    public BgAudioBindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand:" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        FileUtil fileUtil = new FileUtil();
        mMediaPlayer = MediaPlayer.create(this, fileUtil.getUriFromRaw(R.raw.kiss, this));
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        return new MyBinder();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.e(TAG, "unbindService");
    }

    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public void start() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestory");
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public class MyBinder extends Binder {

        public Service getService() {
            return BgAudioBindService.this;
        }
    }

}
