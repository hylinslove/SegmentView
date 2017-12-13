package com.chinastis.segmentview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.chinastis.segmentview.R;

/**
 * Created by xianglong on 2017/12/6.
 */

public class SegmentView extends View{

    private int inColor;
    private int textColor;
    private float textSize;

    private float width;
    private float height;

    private String title1;
    private String title2;

    private float strokeSize;

    private Paint paint;
    private boolean isFirstSelect = true;
    private OnSegmentChangeListener onSegmentChangeListener;

    private ViewPager viewPager;

    public SegmentView(Context context) {
        super(context);
    }

    public SegmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentView);

        inColor = ta.getColor(R.styleable.SegmentView_inColor,Color.GREEN);
        textColor = ta.getColor(R.styleable.SegmentView_textColor,Color.WHITE);
        textSize = ta.getDimension(R.styleable.SegmentView_titleTextSize,40);
        strokeSize = ta.getDimension(R.styleable.SegmentView_strokeSize,6);

        title1 = ta.getString(R.styleable.SegmentView_title1);
        title2 = ta.getString(R.styleable.SegmentView_title2);
        if(title1 == null) {
            title1 = "标题1";
        }
        if(title2 == null) {
            title2 = "标题2";
        }

        ta.recycle();

        initPaint();
    }

    private void initPaint() {
        this.paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setTitle1 (String title1) {
        this.title1 = title1;
        invalidate();
    }
    public void setTitle2 (String title2) {
        this.title2 = title2;
        invalidate();
    }

    public SegmentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY ) {
            width = widthSize;
        } else {
            width = 400;
        }

        if (heightMode == MeasureSpec.EXACTLY ) {
            height = heightSize;
        } else {
            height = 160;
        }
        setMeasuredDimension((int)width,(int)height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getX()>=width/2 && isFirstSelect) {
            setSecondPage();
            if(onSegmentChangeListener!=null) {
                onSegmentChangeListener.segmentChanged(this,1);
            }
            if(viewPager != null) {
                viewPager.setCurrentItem(1);
            }

        } else if (event.getX()<=width/2 && !isFirstSelect){
            setFirstPage();
            if(onSegmentChangeListener!=null) {
                onSegmentChangeListener.segmentChanged(this,0);
            }
            if(viewPager != null) {
                viewPager.setCurrentItem(0);
            }

        }
        return super.onTouchEvent(event);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if(isFirstSelect) {
            float r = height/4;

            paint.setStrokeWidth(0);
            paint.setColor(inColor);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawArc(0,0,r,r,180,90,true,paint);
            canvas.drawArc(0,height-r,r,height,90,90,true,paint);

            canvas.drawRect(0,r/2,r/2,height-(r/2),paint);
            canvas.drawRect(r/2,0,width/2,height,paint);

            paint.setColor(textColor);
            paint.setTextSize(textSize);

            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int baseline = (int) ((height - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawText(title1,width/4,baseline,paint);

            paint.setColor(inColor);
            paint.setStrokeWidth(strokeSize);
            paint.setStyle(Paint.Style.STROKE);

            float padding = paint.getStrokeWidth()/2;
            canvas.drawArc(width-r+padding,padding,width-padding,r-padding,-94,96,false,paint);
            canvas.drawArc(width-r+padding,height-r+padding,width-padding,height-padding,-4,96,false,paint);

            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(width/2,0,width-(r/2),paint.getStrokeWidth(),paint);
            canvas.drawRect(width/2,height-paint.getStrokeWidth(),width-(r/2),height,paint);

            canvas.drawRect(width-paint.getStrokeWidth(),
                    (r/2),
                    width,
                    height-(r/2),
                    paint);

            canvas.drawText(title2,width/4*3,baseline,paint);
        } else {
            float r = height/4;
            paint.setStrokeWidth(strokeSize);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(inColor);
            float padding = paint.getStrokeWidth()/2;

            canvas.drawArc(padding,padding,r-padding,r-padding,176,96,false,paint);
            canvas.drawArc(padding,height-r+padding,r-padding,height-padding,86,96,false,paint);

            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(r/2,0,width/2,padding*2,paint);
            canvas.drawRect(r/2,height-(padding*2),width/2,height,paint);
            canvas.drawRect(0,r/2,padding*2,height-(r/2),paint);

            paint.setTextSize(textSize);

            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int baseline = (int) ((height - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawText(title1,width/4,baseline,paint);

            paint.setStrokeWidth(0);
            paint.setColor(inColor);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawArc(width-r,0,width,r,-90,90,true,paint);
            canvas.drawArc(width-r,height-r,width,height,0,90,true,paint);

            canvas.drawRect(width/2,0,width-(r/2),height,paint);
            canvas.drawRect(width-(r/2),r/2,width,height-(r/2),paint);
            paint.setColor(textColor);
            canvas.drawText(title2,width/4*3,baseline,paint);
        }

    }

    public void startWithViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
    }

    public interface OnSegmentChangeListener {
        void segmentChanged(View view,int index);
    }

    public void setOnSegmentChangeListener (OnSegmentChangeListener listener) {
        this.onSegmentChangeListener = listener;
    }

    public void setFirstPage() {
        isFirstSelect = true;
        invalidate();
    }
    public void setSecondPage() {
        isFirstSelect = false;
        invalidate();
    }
}
