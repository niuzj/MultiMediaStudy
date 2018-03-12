package niu.multimediastudy.chapter4;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import niu.multimediastudy.R;
import niu.multimediastudy.base.SectionBaseActivity;
import niu.multimediastudy.view.C4S2View;

/**
 * Created by Qinlian_niu on 2018/3/12.
 */

public class Section2Activity extends SectionBaseActivity {

    private View childView;

    @Override
    public void addItems() {
        listItems = new String[]{"添加图片", "保存绘制后的图片"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "添加图片":
                mBinding.linearlayout.removeAllViews();
                childView = new C4S2View(this);
                mBinding.linearlayout.addView(childView);
                break;

            case "保存绘制后的图片":
                if (childView != null) {
                    Bitmap bitmap = ((C4S2View) childView).getBitmap();
                    if (bitmap != null) {
                        saveBitmapToGallery(bitmap);
                    }
                } else {
                    Toast.makeText(this, "请先添加图片", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.SIZE, bitmap.getByteCount());
        contentValues.put(MediaStore.Images.Media.HEIGHT, bitmap.getHeight());
        contentValues.put(MediaStore.Images.Media.WIDTH, bitmap.getWidth());
        contentValues.put(MediaStore.Images.Media.TITLE, "涂鸦" + System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri insertUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        OutputStream outputStream = null;
        try {
            outputStream = getContentResolver().openOutputStream(insertUri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
