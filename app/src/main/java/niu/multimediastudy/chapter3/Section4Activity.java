package niu.multimediastudy.chapter3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.LinearLayout;

import niu.multimediastudy.R;
import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/9.
 * 参考资料 http://www.androidchina.net/8149.html
 */

public class Section4Activity extends SectionBaseActivity {
    private Bitmap mBitmap;

    @Override
    public void addItems() {
        listItems = new String[]{"原图", "改变偏移量", "改变颜色系数",
                "灰度效果", "图像反转", "怀旧效果", "去色效果", "高饱和度", "红绿反色"};
    }

    @Override
    public void onClick(String tag) {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
        }
        mBinding.linearlayout.removeAllViews();
        loadBitmap(mBitmap);
        switch (tag) {
            case "原图":
                break;

            case "改变偏移量":
                changeOffset(100, 100, 0, 0);
                break;

            case "改变颜色系数":
                changeFactor(2, 2, 2, 1);
                break;

            case "灰度效果":
                grayFilter();
                break;

            case "图像反转":
                reverseFilter();
                break;

            case "怀旧效果":
                retroFilter();
                break;

            case "去色效果":
                discolorFilter();
                break;

            case "高饱和度":
                highFilter();
                break;

            case "红绿反色":
                reverseColorFilter();
                break;
        }
    }

    /**
     * 改变偏移量
     *
     * @param offsetR 红色偏移量
     * @param offsetG 绿色偏移量
     * @param offsetB 蓝色偏移量
     * @param offsetA 透明度偏移量
     */
    private void changeOffset(float offsetR, float offsetG, float offsetB, float offsetA) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                1, 0, 0, 0, offsetR,
                0, 1, 0, 0, offsetG,
                0, 0, 1, 0, offsetB,
                0, 0, 0, 1, offsetA
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    /**
     * 改变颜色系数
     *
     * @param factorR
     * @param factorG
     * @param factorB
     * @param factorA
     */
    private void changeFactor(float factorR, float factorG, float factorB, float factorA) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                factorR, 0, 0, 0, 0,
                0, factorG, 0, 0, 0,
                0, 0, factorB, 0, 0,
                0, 0, 0, factorA, 0,
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    private void grayFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                0.33f, 0.59f, 0.11f, 0, 0,
                0.33f, 0.59f, 0.11f, 0, 0,
                0.33f, 0.59f, 0.11f, 0, 0,
                0, 0, 0, 1, 0
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    private void reverseFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                -1, 0, 0, 1, 1,
                0, -1, 0, 1, 1,
                0, 0, -1, 1, 1,
                0, 0, 0, 1, 0
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    private void retroFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                0.393f, 0.769f, 0.189f, 0, 0,
                0.349f, 0.686f, 0.168f, 0, 0,
                0.272f, 0.534f, 0.131f, 0, 0,
                0, 0, 0, 1, 0,
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    private void discolorFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                1.5f, 1.5f, 1.5f, 0, -1,
                1.5f, 1.5f, 1.5f, 0, -1,
                1.5f, 1.5f, 1.5f, 0, -1,
                0, 0, 0, 1, 0
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    private void highFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                1.438f, -0.122f, -0.016f, 0, -0.03f,
                -0.062f, 1.378f, -0.016f, 0, 0.05f,
                -0.062f, -0.122f, 1.438f, 0, -0.02f,
                0, 0, 0, 1, 0
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    private void reverseColorFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{
                0, 1, 0, 0, 0,
                1, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0
        });
        createBitmapByMatrix(new ColorMatrixColorFilter(colorMatrix));
    }

    private void createBitmapByMatrix(ColorMatrixColorFilter filter) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(filter);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        loadBitmap(bitmap);
    }

    private void loadBitmap(Bitmap bitmap) {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        imageView.setLayoutParams(layoutParams);
        mBinding.linearlayout.addView(imageView);
        imageView.setImageBitmap(bitmap);
    }

}
