package com.youwu.shopowner.utils_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import static me.goldze.mvvmhabit.utils.ConvertUtils.dp2px;

/**
 * @author: Administrator
 * @date: 2022/9/26
 */
public class HorizontalChartView extends View {

    /**

     * 间隔线画笔

     */

    private Paint paint;

    /**

     * 线的颜色

     */

    private int Color_line = Color.rgb(230, 230, 230);

    /**

     * 字的颜色

     */

    private int Color_font = Color.rgb(51, 51, 51);

    /**

     * 比例图颜色

     */

    private int Color_plan = Color.rgb(22, 85, 164);

    /**

     * 比例图画笔

     */

    private Paint paint_plan;

    /**

     * 比例图高度

     */

    private int plan_height;

    /**

     * 初始化比例

     */

    private float[] ratio = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    /**

     * 文字画笔1

     */

    private Paint paint_font;

    /**

     * 文字画笔2

     */

    private Paint paint_font2;

    /**

     * 线的条数

     */

    private int line_num = 11;

    /**

     * 比例数

     */

    private String ratio_num = "0";

    /**

     * 月份

     */

    private String month_num = "1月";

    public HorizontalChartView(Context context) {

        super(context);

    }

    public HorizontalChartView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

        init(context, attrs);

    }

    public HorizontalChartView(Context context, @Nullable AttributeSet attrs, int defstyleattr) {

        super(context, attrs, defstyleattr);

        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public HorizontalChartView(Context context, @Nullable AttributeSet attrs, int defstyleattr, int defstyleres) {

        super(context, attrs, defstyleattr, defstyleres);

        init(context, attrs);

    }

    /**

     * 初始化

     *

     * @param context

     * @param attrs

     */

    public void init(Context context, AttributeSet attrs) {

        paint = new Paint();

        paint.setColor(Color_line);

        paint_plan = new Paint();

        paint_plan.setColor(Color_plan);

        paint_font = new Paint();

        paint_font.setColor(Color_font);

        paint_font.setTextSize(dp2px( 12));

        paint_font.setAntiAlias(true);

        paint_font.setTextAlign(Paint.Align.CENTER);

        paint_font2 = new Paint();

        paint_font2.setColor(Color_font);

        paint_font2.setTextSize(dp2px( 12));

        paint_font2.setAntiAlias(true);

        paint_font2.setTextAlign(Paint.Align.RIGHT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        int width = getWidth();

        int height = getHeight();

        int lift_width = (int) (width * 0.15);

        int line_width = (int) (width * 0.78);

        //获取底部文字信息
        Paint.FontMetrics fm = paint_font.getFontMetrics();

        int line_length = (int) (height - (fm.bottom - fm.top) - 4);

        plan_height = (int) (line_length / 12 * 0.3);

        for (int i = 0; i < line_num; i++) {

            canvas.save();

            if (i == 0) {

                ratio_num = "0";

            } else {

                ratio_num = i + "0";

            }

            //底部百分比数字

            canvas.drawText(ratio_num, lift_width + i * line_width / 10, height - 10, paint_font);

            //网络线

            canvas.drawLine(lift_width + i * line_width / 10, 0, lift_width + i * line_width / 10, line_length, paint);

            canvas.restore();

        }

        //获取月份文字信息

        Paint.FontMetrics fm1 = paint_font2.getFontMetrics();

        for (int n = 12; n > 0; n--) {

            canvas.save();

            month_num = n + "月";

            //左侧月份

            canvas.drawText(month_num, lift_width / 4 * 3, ((line_length / 12)) * (13 - n) - line_length / 24 + (fm1.bottom - fm1.top) / 2, paint_font2);

            //比例图

            canvas.drawRect(lift_width, line_length / 12 * (13 - n) - (line_length / 24 + plan_height / 2) + fm1.bottom,

                    line_width * (ratio[n - 1] / 100) + lift_width, line_length / 12 * (13 - n) - (line_length / 24 + plan_height / 2) + plan_height + fm1.bottom, paint_plan);

            canvas.restore();

        }

    }

    /**

     * 传入比例信息

     *

     * @param ratio

     */

    public void setratio(float[] ratio) {

        this.ratio = ratio;

    }

}
