package com.isme.shen.slibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.isme.shen.slibrary.utils.DensityUtils;

/**
 * 配合viewpager的向导点点点
 * Created by shen on 2016/8/29.
 */
public class BezierGuidePoint extends View {

    private Context context;
    private int pageNum;
    private float percent;
    private int radius;
    private int interval;
    private int width;
    private int height;
    private Paint paint;
    private Bitmap circleFillBitmap;
    private int padding =0;
    private int stroke = 0;

    public BezierGuidePoint(Context context) {
        this(context,null);
    }

    public BezierGuidePoint(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierGuidePoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        pageNum = 4;//默认点数
        radius = DensityUtils.dp2px(context,10);//默认半径
        interval = DensityUtils.dp2px(context, 20);//默认间隔
        padding = DensityUtils.dp2px(context,3);//默认圆圈间距
        stroke = DensityUtils.dp2px(context,2);//默认圆环的厚度

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    public void setInterval(int interval) {
        this.interval = DensityUtils.dp2px(context,interval);
    }

    public void setRadius(int radius){
        this.radius = DensityUtils.dp2px(context,radius);
    }

    public void setNum(int num){
        this.pageNum = num;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(pageNum*radius*2+(pageNum-1)*interval+padding*2,radius*2+padding*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < pageNum; i++) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(padding+radius + ((interval + radius * 2) * i), padding+radius, radius, paint);
        }

        paint.setStyle(Paint.Style.FILL);

        Path path  = new Path();
        path.moveTo(padding+radius,padding);
        path.quadTo(padding+radius*2+interval/2,padding+radius,padding+radius*3+interval,padding);
        path.lineTo(padding+radius*3+interval,padding+radius*2);
        path.quadTo(padding+radius*2+interval/2,padding+radius,padding+radius,padding+2*radius);
        path.close();
        canvas.drawPath(path,paint);
    }


    private Bitmap getCircleBitmap(Paint paint) {
        Bitmap circleBitmap = Bitmap.createBitmap(2*padding+(int) radius * 2, 2*padding+(int) radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(padding+radius, padding+radius, radius+stroke, paint);
        return circleBitmap;
    }
}