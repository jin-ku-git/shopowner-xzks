package com.youwu.shopowner.xuanfu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.youwu.shopowner.R;

import me.goldze.mvvmhabit.utils.KLog;

import static com.xuexiang.xqrcode.ui.CaptureFragment.onCreate;


/**
 * @author: Administrator
 * @date: 2022/9/27
 */
public class MyButton extends AbastractDragFloatActionButton {

    private String mContent = "去盘点";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public int getLayoutId() {
        return R.layout.custom_button;//拿到你自己定义的悬浮布局
    }
    TextView text_name;
    @Override
    public void renderView(View view) {
        //初始化那些布局
         text_name=view.findViewById(R.id.text_name);

         initView();
    }

    private void initView() {
        text_name.setText(mContent);
    }

    public void setContents(String Content) {
        this.mContent=Content;
        KLog.d("mContent:"+mContent);
        initView();
        KLog.d("text_name:"+text_name.getText().toString());
    }
}
