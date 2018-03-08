package niu.multimediastudy.chapter1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.util.FileUtil;

/**
 * Created by Qinlian_niu on 2018/3/6.
 */

public class Section1Activity extends SectionBaseActivity {

    private static final String TAG = "Section1Activity";
    private static final int CHECK_CAMERA_1 = 1;
    private static final int CHECK_CAMERA_2 = 2;
    private static final int CHECK_STORAGE = 3;
    private static final String FILE_NAME = "test.jpg";

    @Override
    public void addItems() {
        listItems = new String[]{"从Camera应用程序返回数据", "捕获更大的图像", "显示大图像"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "从Camera应用程序返回数据":
                //因为内存的原因，Camera应用程序只会返回很小的缩略图
                if (checkPermission(Manifest.permission.CAMERA, CHECK_CAMERA_1)) {
                    Intent getData = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(getData, 0);
                }
                break;

            case "捕获更大的图像":
                if (checkPermission(Manifest.permission.CAMERA, CHECK_CAMERA_2)) {
                    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, CHECK_STORAGE)) {
                        FileUtil fileUtil = new FileUtil();
                        Intent getData = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getData.putExtra(MediaStore.EXTRA_OUTPUT, fileUtil.getUri(FILE_NAME, this));
                        startActivityForResult(getData, 1);
                    }
                }
                break;

            case "显示大图像":
                mBinding.linearlayout.removeAllViews();
                FileUtil fileUtil = new FileUtil();
                String filePath = fileUtil.getFilePath(FILE_NAME);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, options);
                Log.e(TAG, "测量的宽高是：" + options.outWidth + "x" + options.outHeight);
                options.inJustDecodeBounds = false;
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
                if (bitmap != null) {
                    Log.e(TAG, "操作后的宽高是：" + bitmap.getWidth() + "x" + bitmap.getHeight());
                    ImageView imageView = new ImageView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
                    imageView.setLayoutParams(layoutParams);
                    mBinding.linearlayout.addView(imageView);
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "Bitmap == null");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mBinding.linearlayout.removeAllViews();
            ImageView imageView = null;
            LinearLayout.LayoutParams layoutParams = null;
            Bitmap bitmap = null;
            switch (requestCode) {
                case 0:
                    Bundle extras = data.getExtras();
                    bitmap = (Bitmap) extras.get("data");
                    Log.e(TAG, "返回的宽高为：" + bitmap.getWidth() + "x" + bitmap.getHeight());
                    imageView = new ImageView(this);
                    layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
                    imageView.setLayoutParams(layoutParams);
                    mBinding.linearlayout.addView(imageView);
                    imageView.setImageBitmap(bitmap);
                    break;

                case 1:
                    //捕获全尺寸图片时，data为null
                    FileUtil fileUtil = new FileUtil();
                    String filePath = fileUtil.getFilePath(FILE_NAME);
                    bitmap = BitmapFactory.decodeFile(filePath);
                    Log.e(TAG, "返回的宽高为：" + bitmap.getWidth() + "x" + bitmap.getHeight());
                    if (bitmap != null) {
                        imageView = new ImageView(this);
                        layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
                        imageView.setLayoutParams(layoutParams);
                        mBinding.linearlayout.addView(imageView);
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(this, "Bitmap is null", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CHECK_CAMERA_1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent getData = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(getData, 0);
                }
                break;

            case CHECK_CAMERA_2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, CHECK_STORAGE)) {
                        FileUtil fileUtil = new FileUtil();
                        Intent getData = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getData.putExtra(MediaStore.EXTRA_OUTPUT, fileUtil.getUri(FILE_NAME, this));
                        startActivityForResult(getData, 1);
                    }
                }
                break;

            case CHECK_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    FileUtil fileUtil = new FileUtil();
                    Intent getData = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getData.putExtra(MediaStore.EXTRA_OUTPUT, fileUtil.getUri(FILE_NAME, this));
                    startActivityForResult(getData, 1);
                }
                break;
        }
    }
}
