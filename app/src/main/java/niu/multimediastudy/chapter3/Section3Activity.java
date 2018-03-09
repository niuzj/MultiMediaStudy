package niu.multimediastudy.chapter3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.LinearLayout;

import niu.multimediastudy.R;
import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/9.
 */

public class Section3Activity extends SectionBaseActivity {

    private Bitmap mBitmap;

    @Override
    public void addItems() {
        listItems = new String[]{"清空", "加载原图",
                "矩阵水平平移", "矩阵垂直平移", "矩阵旋转", "矩阵缩放", "矩阵x颠倒", "矩阵y颠倒", "矩阵xy颠倒",
                "Translate", "Scale", "Rotate",
                "修改上述方式造成的显示不全bug"};
    }

    @Override
    public void onClick(String tag) {
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
        }
        mBinding.linearlayout.removeAllViews();
        loadBitmap(mBitmap);
        switch (tag) {
            case "清空":
                mBinding.linearlayout.removeAllViews();
                break;

            case "加载原图":
                break;

            case "矩阵水平平移":
                matrixTranslateX(50);
                break;

            case "矩阵垂直平移":
                matrixTranslateY(50);
                break;

            case "矩阵旋转":
                matrixRotate(30);
                break;

            case "矩阵缩放":
                matrixScale(1.1f);
                break;

            case "矩阵x颠倒":
                matrixMirrorX();
                break;

            case "矩阵y颠倒":
                matrixMirrorY();
                break;

            case "矩阵xy颠倒":
                matrixMirrorXY();
                break;

            case "Translate":
                Matrix matrixTranslate = new Matrix();
                matrixTranslate.setTranslate(10, 10);
                createBitmapByMatrix(matrixTranslate);
                break;

            case "Scale":
                Matrix matrixScale = new Matrix();
                matrixScale.setScale(0.5f, 0.5f, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
                createBitmapByMatrix(matrixScale);
                break;

            case "Rotate":
                Matrix matrixRotate = new Matrix();
                matrixRotate.setRotate(30, mBitmap.getWidth() / 2, mBitmap.getHeight());
                createBitmapByMatrix(matrixRotate);
                break;

            case "修改上述方式造成的显示不全bug":
                //不需要创建Canvas了，并且大小就是能够显示全部图像的大小
                Matrix matrixFix = new Matrix();
                matrixFix.setRotate(30, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
                //内部应该是通过这些参数计算出最终大小的，所以可以修改，以显示想要的大小
                Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrixFix, false);
                loadBitmap(bitmap);
                break;
        }
    }

    /**
     * 水平平移
     *
     * @param translateX
     */
    private void matrixTranslateX(float translateX) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                1, 0, translateX,
                0, 1, 0,
                0, 0, 1
        });
        createBitmapByMatrix(matrix);
    }

    /**
     * 垂直平移
     *
     * @param translateY
     */
    private void matrixTranslateY(float translateY) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                1, 0, 0,
                0, 1, translateY,
                0, 0, 1
        });
        createBitmapByMatrix(matrix);
    }

    /**
     * 旋转
     *
     * @param angle
     */
    private void matrixRotate(float angle) {
        float radians = (float) ((angle / 180) * Math.PI);
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                (float) Math.cos(radians), (float) -Math.sin(radians), 0,
                (float) Math.sin(radians), (float) Math.cos(radians), 0,
                0, 0, 1
        });
        createBitmapByMatrix(matrix);
    }

    /**
     * 缩放比例
     *
     * @param scaleRatio
     */
    private void matrixScale(float scaleRatio) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                scaleRatio, 0, 0,
                0, scaleRatio, 0,
                0, 0, 1
        });
        createBitmapByMatrix(matrix);
    }

    /**
     * 镜像x坐标颠倒
     */
    private void matrixMirrorX() {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                -1, 0, mBitmap.getWidth(),
                0, 1, 0,
                0, 0, 1
        });
        createBitmapByMatrix(matrix);
    }

    /**
     * 镜像y坐标颠倒
     */
    private void matrixMirrorY() {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                1, 0, 0,
                0, -1, mBitmap.getHeight(),
                0, 0, 1
        });
        createBitmapByMatrix(matrix);
    }

    /**
     * 镜像xy坐标颠倒
     */
    private void matrixMirrorXY() {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{
                -1, 0, mBitmap.getWidth(),
                0, -1, mBitmap.getHeight(),
                0, 0, 1
        });
        createBitmapByMatrix(matrix);
    }

    private void createBitmapByMatrix(Matrix matrix) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(mBitmap, matrix, null);
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
