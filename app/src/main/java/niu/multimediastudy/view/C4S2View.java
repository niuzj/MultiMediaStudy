package niu.multimediastudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import niu.multimediastudy.R;

/**
 * Created by Qinlian_niu on 2018/3/12.
 */

public class C4S2View extends View {

    Canvas mCanvas;
    Paint mPaint;
    Path mPath;
    Bitmap mBitmap;

    public C4S2View(Context context) {
        super(context);
        init();
    }

    public C4S2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public C4S2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beautiful);
        mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawBitmap(bitmap, 0, 0, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPaint);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mPath.lineTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPaint);
                invalidate();
                break;
        }
        return true;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
