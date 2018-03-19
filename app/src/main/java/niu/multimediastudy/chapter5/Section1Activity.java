package niu.multimediastudy.chapter5;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.bean.AudioBean;

/**
 * Created by Qinlian_niu on 2018/3/14.
 */

public class Section1Activity extends SectionBaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    private String[] projection = new String[]{
            //文件路径
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media._ID,
            //标题
            MediaStore.Audio.Media.TITLE,
            //显示的名称
            MediaStore.Audio.Media.DISPLAY_NAME,
            //MIME_TYPE
            MediaStore.Audio.Media.MIME_TYPE,
            //艺术家
            MediaStore.Audio.Media.ARTIST,
            //唱片集
            MediaStore.Audio.Media.ALBUM,
            //报警
            MediaStore.Audio.Media.IS_ALARM,
            //音乐
            MediaStore.Audio.Media.IS_MUSIC,
            //通知
            MediaStore.Audio.Media.IS_NOTIFICATION,
            //铃声
            MediaStore.Audio.Media.IS_RINGTONE,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATE_MODIFIED,
    };

    @Override
    public void addItems() {
        listItems = new String[]{"查询"};
    }

    @Override
    public void onClick(String tag) {

        switch (tag) {
            case "查询":
                //MediaStore.Audio.Media.INTERNAL_CONTENT_URI 系统内置的音频在这里
                Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
                //总共查询的列数
                Log.e(TAG, "cursor.getColumnCount == " + cursor.getColumnCount());
                //总共有多少条数据
                Log.e(TAG, "cursor.getCount == " + cursor.getCount());
                while (cursor.moveToNext()) {
                    AudioBean audioBean = new AudioBean();
                    audioBean.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    audioBean.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                    audioBean.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    audioBean.setDiplay_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
                    audioBean.setMime_type(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE)));
                    audioBean.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    audioBean.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    audioBean.setIs_alarm(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_ALARM)));
                    audioBean.setIs_music(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)));
                    audioBean.setIs_notification(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_NOTIFICATION)));
                    audioBean.setIs_ringtone(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_RINGTONE)));
                    audioBean.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
                    audioBean.setDate_added(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)));
                    audioBean.setDate_modified(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED)));
                    Log.e(TAG, audioBean.toString());
                }
                cursor.close();
                break;
        }
    }
}
