package niu.multimediastudy.chapter4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import niu.multimediastudy.base.SectionBaseActivity;

/**
 * Created by Qinlian_niu on 2018/3/12.
 */

public class Section1Activity extends SectionBaseActivity implements View.OnTouchListener {

    Canvas mCanvas = null;
    Path mPath = null;
    Paint mPaint = null;

    @Override
    public void addItems() {
        listItems = new String[]{"加载ImageView"};
    }

    @Override
    public void onClick(String tag) {
        mBinding.linearlayout.removeAllViews();
        switch (tag) {
            case "加载ImageView":
                Bitmap bitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(bitmap);
                mCanvas.drawColor(Color.WHITE);
                mPath = new Path();
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mPaint.setColor(Color.RED);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(10);
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);
                imageView.setOnTouchListener(this);
                mBinding.linearlayout.addView(imageView);
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPaint);
                v.invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPaint);
                v.invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mPath.lineTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPaint);
                v.invalidate();
                break;
        }
        return true;
    }
}
