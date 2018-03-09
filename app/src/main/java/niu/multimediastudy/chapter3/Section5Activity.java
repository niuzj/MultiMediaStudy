package niu.multimediastudy.chapter3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import niu.multimediastudy.R;
import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/8.
 */

public class Section5Activity extends SectionBaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    private final int TO_GALLERY = 1;
    private Bitmap mBitmapSrc;
    private Bitmap mBitmapDst;


    @Override
    public void addItems() {
        listItems = new String[]{"清空", "从Gallery中获取Src图片", "加载Dst图片", "二图组合"};
    }

    @Override
    public void onClick(String tag) {
        switch (tag) {
            case "清空":
                mBinding.linearlayout.removeAllViews();
                break;

            case "从Gallery中获取Src图片":
                if (mBitmapSrc == null) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, TO_GALLERY);
                } else {
                    loadBitmap(mBitmapSrc);
                }
                break;

            case "加载Dst图片":
                if (mBitmapDst == null) {
                    mBitmapDst = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
                }
                loadBitmap(mBitmapDst);
                break;

            case "二图组合":
                if (mBitmapDst != null && mBitmapSrc != null)
                    combine();
                break;
        }
    }

    private void combine() {
        int width = Math.max(mBitmapDst.getWidth(), mBitmapSrc.getWidth());
        int height = Math.max(mBitmapDst.getHeight(), mBitmapSrc.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int index = canvas.saveLayer(0, 0, width, height, paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmapDst, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        canvas.drawBitmap(mBitmapSrc, (mBitmapDst.getWidth() - mBitmapSrc.getWidth()) / 2, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(index);
        loadBitmap(bitmap);
    }

    private void loadBitmap(Bitmap bitmap) {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        imageView.setLayoutParams(layoutParams);
        mBinding.linearlayout.addView(imageView);
        imageView.setImageBitmap(bitmap);
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
                    mBitmapSrc = BitmapFactory.decodeStream(i, null, options);
                    if (mBitmapSrc != null) {
                        Log.e(TAG, "压缩后的宽高：" + mBitmapSrc.getWidth() + "x" + mBitmapSrc.getHeight());
                        loadBitmap(mBitmapSrc);
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
