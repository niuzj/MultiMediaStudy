package niu.multimediastudy.chapter6;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/15.
 */

public class Section1Activity extends SectionBaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    private final int TO_RECORD = 1;
    private final int CHECK_RECORD = 2;
    private Uri mUri;
    private MediaPlayer mMediaPlayer;

    @Override
    public void addItems() {
        listItems = new String[]{"音频捕获", "音频播放", "停止播放"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "音频捕获":
                if (checkPermission(Manifest.permission.RECORD_AUDIO, CHECK_RECORD)) {
                    Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                    startActivityForResult(intent, TO_RECORD);
                    /*ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.Audio.Media.DATE_ADDED, System.currentTimeMillis());
                    contentValues.put(MediaStore.Audio.Media.DATE_MODIFIED, System.currentTimeMillis());
                    contentValues.put(MediaStore.Audio.Media.ALBUM, "Test");
                    contentValues.put(MediaStore.Audio.Media.ARTIST, "Niu");
                    contentValues.put(MediaStore.Audio.Media.TITLE, "测试");
                    contentValues.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mpeg");
                    contentValues.put(MediaStore.Audio.Media.DISPLAY_NAME, "我的");
                    contentValues.put(MediaStore.Audio.Media.IS_MUSIC, 1);
                    Uri insertUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues);
                    Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, insertUri);
                    startActivityForResult(intent, TO_RECORD);*/
                }
                break;

            case "音频播放":
                if (mUri != null && (mMediaPlayer == null || (mMediaPlayer != null && !mMediaPlayer.isPlaying()))) {
                    mMediaPlayer = MediaPlayer.create(this, mUri);
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.start();
                } else if (mUri == null) {
                    Toast.makeText(this, "请先录制音频", Toast.LENGTH_SHORT).show();
                }
                break;

            case "停止播放":
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TO_RECORD:
                    mUri = data.getData();
                    String uriString = mUri.toString();
                    Log.e(TAG, uriString);
                    String[] split = uriString.split("/");
                    ContentValues contentValues = new ContentValues();
                    //貌似只有display_name可以改
                    contentValues.put(MediaStore.Audio.Media.DISPLAY_NAME, "我的");
                    getContentResolver().update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues, "_id=?", new String[]{split[split.length - 1]});

                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            startActivityForResult(intent, TO_RECORD);
        } else {
            finish();
        }
    }
}
