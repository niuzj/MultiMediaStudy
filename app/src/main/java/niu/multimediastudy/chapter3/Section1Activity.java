package niu.multimediastudy.chapter3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/8.
 */

public class Section1Activity extends SectionBaseActivity {

    private final int TO_GALLERY = 1;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void addItems() {
        listItems = new String[]{"调起Callery应用程序"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "调起Callery应用程序":
                mBinding.linearlayout.removeAllViews();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, TO_GALLERY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TO_GALLERY) {
                Uri uri = data.getData();
                InputStream inputStream = null;
                InputStream i = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream, null, options);
                    Log.e(TAG, "原始宽高：" + options.outWidth + "x" + options.outHeight);
                    float widthRatio = options.outWidth * 1.0f / 800;
                    float heightRatio = options.outHeight * 1.0f / 800;
                    if (widthRatio >= 1.0f || heightRatio >= 1.0f) {
                        //只能是2的次方
                        //options.inSampleSize = (int) Math.ceil(Math.max(widthRatio, heightRatio));
                        options.inSampleSize = 8;
                    }
                    options.inJustDecodeBounds = false;
                    i = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(i, null, options);
                    if (bitmap != null) {
                        Log.e(TAG, "压缩后的宽高：" + bitmap.getWidth() + "x" + bitmap.getHeight());
                        ImageView imageView = new ImageView(this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
                        imageView.setLayoutParams(layoutParams);
                        mBinding.linearlayout.addView(imageView);
                        imageView.setImageBitmap(bitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (i != null) {
                        try {
                            i.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消了", Toast.LENGTH_SHORT);
        }
    }
}
