package com.youwu.shopowner.ui.order_record;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author: Administrator
 * @date: 2022/9/27
 */
public class DragImageView extends AppCompatImageView {
    String TAG = "DragImageView";

    public DragImageView(Context context) {
        this(context, null);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置在父布局中的边界
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public void initEdge(int l,int t,int r,int b) {
        edgeLeft = l;
        edgeTop = t;
        edgeRight = r;
        edgeBottom = b;
    }

    int edgeLeft, edgeTop, edgeRight, edgeBottom;
    int lastX, lastY, movex, movey, dx, dy;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                movex = lastX;
                movey = lastY;
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int) event.getRawX() - lastX;
                dy = (int) event.getRawY() - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                if (left < edgeLeft) {
                    left = edgeLeft;
                    right = left + getWidth();
                }
                if (right > edgeRight) {
                    right = edgeRight;
                    left = right - getWidth();
                }
                if (top < edgeTop) {
                    top = edgeTop;
                    bottom = top + getHeight();
                }
                if (bottom > edgeBottom) {

                    bottom = edgeBottom;
                    top = bottom - getHeight();
                }

                layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //避免滑出触发点击事件
                if ((int) (event.getRawX() - movex) != 0
                        || (int) (event.getRawY() - movey) != 0) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}