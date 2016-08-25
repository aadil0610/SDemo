package com.isme.shen.slibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shen on 2016/8/25.
 */
public class LoadingCircle extends View {

    private int circleNum = 5;//圆的数量
    private float radius = 30;//圆的半径
    private int interval = 50;//圆之间的间隔
    private float percent = 0;//百分比
    private int width;
    private int height;
    private Bitmap circleFillBitmap;
    private Paint paint;
    private List<Bitmap> circles;

    public LoadingCircle(Context context) {
        this(context, null);
    }

    public LoadingCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);
        circles = new ArrayList<>();
        for (int i = 0; i < circleNum - 1; i++) {
            circles.add(getCircleBitmap(paint));
        }
        paint.setStyle(Paint.Style.FILL);
        circleFillBitmap = getCircleBitmap(paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        drawCircle(canvas);

        Matrix matrix = new Matrix();
        int offsetX = circleFillBitmap.getWidth() / 2;
        int offsetY = circleFillBitmap.getHeight() / 2;
        matrix.postTranslate(-offsetX, -offsetY);
        matrix.postRotate(percent/180,radius*2+interval/2,radius);
        percent = (float) (percent + 0.01);
        matrix.postTranslate(offsetX,offsetY);
        canvas.drawBitmap(circleFillBitmap, matrix, null);
        invalidate();
    }

    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < circleNum; i++) {
            if (i == 0) {
                canvas.drawBitmap(circleFillBitmap, 0, height / 2 - radius, null);
            } else {
                canvas.drawBitmap(circles.get(i-1), i * radius * 2 + i * interval, height / 2 - radius, null);
            }
        }

    }

    private Bitmap getCircleBitmap(Paint paint) {
        Bitmap circleBitmap = Bitmap.createBitmap((int) radius * 2, (int) radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(radius, radius, radius, paint);
        return circleBitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
    }
}
