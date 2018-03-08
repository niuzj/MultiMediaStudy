package niu.multimediastudy.chapter1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.bean.ImageBean;

/**
 * Created by Qinlian_niu on 2018/3/7.
 */

public class Section2Activity extends SectionBaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void addItems() {
        listItems = new String[]{"ContentResolver之add", "ContentResolver之delete", "ContentResolver之update", "ContentResolver之query"};
    }

    @Override
    public void onClick(String tag) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = null;
        Uri uri = null;
        Cursor cursor = null;
        switch (tag) {
            case "ContentResolver之add":
                //默认的capacity是8
                contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "this is display_name");
                contentValues.put(MediaStore.Images.Media.DESCRIPTION, "lalalalala");
                contentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
                contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/peng");
                uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                Log.e(TAG, "add uri == " + uri.toString());
                break;

            case "ContentResolver之delete":
                int rows = contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_display_name = ? or description = ?",
                        new String[]{"display_name change", "lalalalala"});
                Log.e(TAG, "一共删除了：" + rows + "行");
                break;

            case "ContentResolver之update":
                contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "display_name change");
                int row = contentResolver.update(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues, "_display_name = ?",
                        new String[]{"this is display_name"});
                Log.e(TAG, "row == " + row);
                break;

            case "ContentResolver之query":
                cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DESCRIPTION,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media.DATE_TAKEN,
                        MediaStore.Images.Media.MIME_TYPE,
                        MediaStore.Images.Media.ORIENTATION}, "mime_type = ?", new String[]{"image/jpeg"}, null);
                while (cursor.moveToNext()) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.setDate_added(cursor.getFloat(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)));
                    imageBean.setDate_taken(cursor.getFloat(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
                    imageBean.setDescription(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION)));
                    imageBean.setDisplay_name(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                    imageBean.setMime_type(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)));
                    imageBean.setOrientation(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION)));
                    Log.e(TAG, imageBean.toString());
                }
                cursor.close();
                break;
        }
    }

}
