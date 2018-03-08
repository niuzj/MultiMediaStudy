package niu.multimediastudy.chapter1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.bean.ExifBean;
import niu.multimediastudy.bean.ImageBean;

/**
 * Created by Qinlian_niu on 2018/3/7.
 */

public class Section3Activity extends SectionBaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    private final static int CHECK_CAMERA = 1;
    private final static int CHECK_STORAGE = 2;
    private final static int TAKE_PICTURE = 3;
    private Uri insertUri;
    private String imageFilePath;

    @Override
    public void addItems() {
        listItems = new String[]{"将拍摄的照片存入相册",
                "读取相册中的信息",
                "获取相册中的缩略图",
                "查看内部元数据EXIF"};
    }

    @Override
    public void onClick(String tag) {
        mBinding.linearlayout.removeAllViews();
        switch (tag) {
            case "将拍摄的照片存入相册":
                if (checkPermission(Manifest.permission.CAMERA, CHECK_CAMERA)) {
                    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, CHECK_STORAGE)) {
                        takePicture();
                    }
                }
                break;

            case "读取相册中的信息":
                seeAlbum();
                break;

            case "获取相册中的缩略图":
                Toast.makeText(this, "还没做", Toast.LENGTH_SHORT).show();
                break;

            case "查看内部元数据EXIF":

                break;
        }
    }

    /**
     * 拍照
     */
    private void takePicture() {
        ContentValues contentValues = new ContentValues();
        //时间戳
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + "display_name");
        contentValues.put(MediaStore.Images.Media.TITLE, System.currentTimeMillis() + "title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "come from MultiMediaStudy");
        ContentResolver contentResolver = getContentResolver();
        insertUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, insertUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    /**
     * 查看相册中的信息
     */
    private void seeAlbum() {
        ContentResolver contentResolver = getContentResolver();
        String[] projection = new String[]{MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DESCRIPTION, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.LATITUDE/*维度*/, MediaStore.Images.Media.LONGITUDE/*经度*/};
        Cursor query = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        while (query.moveToNext()) {
            ImageBean imageBean = new ImageBean();
            imageBean.setDate_taken(query.getFloat(query.getColumnIndex(projection[0])));
            imageBean.setDescription(query.getString(query.getColumnIndex(projection[1])));
            imageBean.setDisplay_name(query.getString(query.getColumnIndex(projection[2])));
            imageBean.setMime_type(query.getString(query.getColumnIndex(projection[3])));
            //图片路径
            imageBean.setData(query.getString(query.getColumnIndexOrThrow(projection[4])));
            imageBean.setTitle(query.getString(query.getColumnIndex(projection[5])));
            imageBean.setLat(query.getDouble(query.getColumnIndex(projection[6])));
            imageBean.setLon(query.getDouble(query.getColumnIndex(projection[7])));
            imageFilePath = imageBean.getData();
            Log.e(TAG, "imageBean == " + imageBean.toString());
            //seeEXIF();
            //changeEXIF();
        }
        query.close();
    }

    /**
     * 查看可交换的图像文件格式相关数据（部分）
     */
    private void seeEXIF() {
        try {
            ExifInterface exifInterface = new ExifInterface(imageFilePath);
            ExifBean exifBean = new ExifBean();
            exifBean.setArtist(exifInterface.getAttribute(ExifBean.ExifAttr.Artist.name));
            exifBean.setCopyRight(exifInterface.getAttribute(ExifBean.ExifAttr.Copyright.name));
            exifBean.setImageDescription(exifInterface.getAttribute(ExifBean.ExifAttr.ImageDescription.name));
            exifBean.setSoftWare(exifInterface.getAttribute(ExifBean.ExifAttr.Software.name));
            exifBean.setUserComment(exifInterface.getAttribute(ExifBean.ExifAttr.UserComment.name));
            Log.e(TAG, "ExifBean : " + exifBean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeEXIF() {
        try {
            ExifInterface exifInterface = new ExifInterface(imageFilePath);
            exifInterface.setAttribute(ExifBean.ExifAttr.Artist.name, "MultiMediaStudy");
            imageFilePath = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PICTURE && insertUri != null) {
                InputStream inputStream1 = null;
                InputStream inputStream2 = null;
                try {
                    inputStream1 = getContentResolver().openInputStream(insertUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream1, null, options);
                    Log.e(TAG, "原始尺寸：" + options.outWidth + "x" + options.outHeight);
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 4;
                    //这里要重新获取流
                    inputStream2 = getContentResolver().openInputStream(insertUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream2, null, options);
                    if (bitmap != null) {
                        Log.e(TAG, "压缩后的尺寸：" + bitmap.getWidth() + "x" + bitmap.getHeight());
                        ImageView imageView = new ImageView(this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
                        imageView.setLayoutParams(layoutParams);
                        mBinding.linearlayout.addView(imageView);
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(this, "Bitmap is null", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream1 != null)
                            inputStream1.close();
                        if (inputStream2 != null)
                            inputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CHECK_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, CHECK_STORAGE)) {
                        takePicture();
                    }
                }
                break;

            case CHECK_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                }
                break;
        }
    }
}
