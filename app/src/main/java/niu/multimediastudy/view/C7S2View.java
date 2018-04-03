package niu.multimediastudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by Qinlian_niu on 2018/3/19.
 */

public class C7S2View extends View {

    private final String TAG = this.getClass().getSimpleName();

    private Paint mPaint;

    private int width;
    private int x = 0;
    private int xOffset = 10;
    private float[] fps;
    private int lineCount;
    private int currentLineCount = -1;

    public C7S2View(Context context) {
        super(context);
        init(context);
    }

    public C7S2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public C7S2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;

        lineCount = width / 20;
        fps = new float[lineCount * 4];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(0, 150, width, 150, mPaint);
        canvas.drawLines(fps, mPaint);
    }

    public void setHeight(double curHeight) {
        Log.e(TAG, TAG + "  curHeight == " + curHeight);
        currentLineCount += 1;
        if (currentLineCount < lineCount) {
            int start = currentLineCount * 4;
            fps[start] = x;
            fps[start + 1] = 150;
            fps[start + 2] = x;
            fps[start + 3] = (float) (-150 * curHeight + 150);
            x += xOffset;
            invalidate();
        }
    }

}
