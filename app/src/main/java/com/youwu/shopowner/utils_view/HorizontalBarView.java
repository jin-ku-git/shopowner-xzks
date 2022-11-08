package com.youwu.shopowner.utils_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2022/9/26
 */
public class HorizontalBarView extends View {

    private ArrayList<HoBarEntity> hoBarEntityList = new ArrayList<>();
    private float barStartX = 0f;//
    private Paint mContentPaint;
    private Paint mbarPaint;
    private Paint mCountPaint;
    private Paint mLinePaint;
    private float barHeight = dp2px(15); //bar的高度
    private float barInterval = dp2px(15);//bar之间的距离
    private int mContentPaintColor = Color.parseColor("#9e9e9e"); //最左边文字的颜色
    private int mbarPaintColor = Color.parseColor("#5087EC");//bar的颜色
    private int mCountPaintColor = Color.parseColor("#5e5e5e");//最右边文字的颜色
    private int mLinePaintColor = Color.parseColor("#ededed");//线的颜色
    private int contentTextSize = 28;//最左边文字的大小
    private int countTextSize = 30;//最右边文字的大小
    private int viewIntervar = 10;//view的距离
    private float topAndBottomInterval = dp2px(10);//上下边距的距离（上下线与bar的距离）
    private float rightInterval = dp2px(10);//最右侧文字和右边距离
    private int defaltHeight = 0;
    private int textMaxWidth = 0;
    private int maxCount = 0;
    private float countMaxWidth = 0;
    private float contentTextHeight = 0;
    private float countTextHeight = 0;
    private Rect rect;//柱状图矩形
    private List<Rect> rectList =new ArrayList<>();

    private OnItemClickListener listener;
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(String name,int num);
    }

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    public HorizontalBarView(Context context) {
        this(context, null);
    }

    public HorizontalBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    public float getBarHeight() {
        return barHeight;
    }

    public float getBarInterval() {
        return barInterval;
    }

    public int getmContentPaintColor() {
        return mContentPaintColor;
    }

    public int getMbarPaintColor() {
        return mbarPaintColor;
    }

    public int getmCountPaintColor() {
        return mCountPaintColor;
    }

    public int getmLinePaintColor() {
        return mLinePaintColor;
    }

    public int getContentTextSize() {
        return contentTextSize;
    }

    public int getCountTextSize() {
        return countTextSize;
    }

    public int getViewIntervar() {
        return viewIntervar;
    }

    public float getTopAndBottomInterval() {
        return topAndBottomInterval;
    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initPaints() {
        mContentPaint = new Paint();
        mContentPaint.setAntiAlias(true);
        mContentPaint.setStyle(Paint.Style.FILL);
        mContentPaint.setColor(mContentPaintColor);
        mContentPaint.setTextSize(contentTextSize);
        mContentPaint.setTextAlign(Paint.Align.RIGHT);
        mContentPaint.setStrokeCap(Paint.Cap.ROUND);

        mbarPaint = new Paint();
        mbarPaint.setAntiAlias(true);
        mbarPaint.setStyle(Paint.Style.FILL);
        mbarPaint.setColor(mbarPaintColor);
        mbarPaint.setStrokeCap(Paint.Cap.ROUND);


        mCountPaint = new Paint();
        mCountPaint.setAntiAlias(true);
        mCountPaint.setStyle(Paint.Style.FILL);
        mCountPaint.setTextSize(countTextSize);
        mCountPaint.setColor(mCountPaintColor);
        mCountPaint.setStrokeCap(Paint.Cap.ROUND);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setTextSize(countTextSize);
        mLinePaint.setColor(mLinePaintColor);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setStrokeWidth(dp2px(0.8f));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getScreenWidth();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //计算高度
            computeHeightAndTextMaxWidth();
            height = defaltHeight;
        }
        setMeasuredDimension(width, height);
    }

    //计算高度
    private void computeHeightAndTextMaxWidth() {
        if (hoBarEntityList.size() == 0) {
            defaltHeight = getPaddingTop() + getPaddingBottom();
        } else {
            defaltHeight = (int) ((int) (getPaddingTop() + getPaddingBottom() + barHeight * hoBarEntityList.size() + (hoBarEntityList.size() - 1) * barInterval) + 2 * topAndBottomInterval);
            for (int i = 0; i < hoBarEntityList.size(); i++) {
                float tempWidth = mContentPaint.measureText(hoBarEntityList.get(i).content);
                if (tempWidth > textMaxWidth) {
                    textMaxWidth = (int) tempWidth;
                }
                //获取文字高度
                if (contentTextHeight <= 0) {
                    Rect rect = new Rect();
                    mContentPaint.getTextBounds(hoBarEntityList.get(i).content, 0, hoBarEntityList.get(i).content.length(), rect);
                    contentTextHeight = rect.width();
                }
                if (countTextHeight <= 0) {
                    Rect rect = new Rect();
                    mCountPaint.getTextBounds(hoBarEntityList.get(i).count + "", 0, (hoBarEntityList.get(i).count + "").length(), rect);
                    countTextHeight = rect.width();
                }
                if (hoBarEntityList.get(i).count > maxCount) {
                    maxCount = hoBarEntityList.get(i).count;
                }
            }
            countMaxWidth = mCountPaint.measureText(maxCount + "");
            barStartX = getPaddingLeft() + textMaxWidth + viewIntervar;
            Log.e("TAG", "textMaxWidth===" + textMaxWidth + "    countMaxWidth===" + countMaxWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算最长的bar的长度
        float maxBarWidth = getMeasuredWidth() - getPaddingRight() - barStartX - countMaxWidth - 2 * viewIntervar - rightInterval;
        float perBarWidth = 0;
        if (maxCount > 0) {
            perBarWidth = maxBarWidth / maxCount;
        }

        //绘制bar
        float barStartY = getPaddingTop() + topAndBottomInterval;
        float barEndX = 0;
        float barEndY = 0;
        //绘制content
        float contentTextStart = barStartX - viewIntervar;

        //绘制上边的线
        canvas.drawLine(barStartX, getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getPaddingTop(), mLinePaint);
        //绘制下边的线
        canvas.drawLine(barStartX, getMeasuredHeight() - getPaddingBottom(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom(), mLinePaint);
        //绘制左边的线
        canvas.drawLine(barStartX, getPaddingTop(), barStartX, getMeasuredHeight() - getPaddingBottom(), mLinePaint);
        rectList.clear();
        for (int i = 0; i < hoBarEntityList.size(); i++) {
            barEndX = barStartX + perBarWidth * hoBarEntityList.get(i).count;
            barEndY = barStartY + barHeight;
            Log.e("TAG", "barStartX-barEndX===" + (barStartX - barEndX));
            //绘制bar
            canvas.drawRect(barStartX, barStartY, barEndX, barEndY, mbarPaint);
            rect = new Rect((int)barStartX, (int)barStartY,(int) barEndX, (int)barEndY);
            rectList.add(rect);
            //绘制content
            //计算基线
            Paint.FontMetricsInt contentfontMetrics = mContentPaint.getFontMetricsInt();
            float contentBaseLine = barStartY + barHeight / 2 + -(contentfontMetrics.bottom - contentfontMetrics.top) / 2 - contentfontMetrics.top;
            canvas.drawText(hoBarEntityList.get(i).content, contentTextStart, contentBaseLine, mContentPaint);

            //绘制count
            Paint.FontMetricsInt fontMetrics = mCountPaint.getFontMetricsInt();
            float countBaseLine = barStartY + barHeight / 2 + -(fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(hoBarEntityList.get(i).count + "份", barEndX + viewIntervar * 2, countBaseLine, mCountPaint);

            barStartY = barEndY + barInterval;

//
        }


    }



    /**
     * 柱状图touch事件
     * 获取触摸位置计算属于哪个月份的
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < hoBarEntityList.size(); i++) {
                    if(rectList.get(i).contains(x,y)) {
                        listener.onClick(hoBarEntityList.get(i).content,hoBarEntityList.get(i).count);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }





    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    private int getScreenheight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


    public void setHoBarData(ArrayList<HoBarEntity> list) {
        hoBarEntityList.clear();
        hoBarEntityList.addAll(list);
        requestLayout();
        invalidate();
    }

    public static class HoBarEntity {
        public String content;
        public int count;
    }

}
