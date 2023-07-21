package com.youwu.shopowner.ui.fragment.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youwu.shopowner.R;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Created by Raul_lsj on 2018/3/28.
 */

public class ScrollRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {

    public ScrollRightAdapter(int layoutResId, int sectionHeadResId, List<ScrollBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ScrollBean item) {
        helper.setText(R.id.right_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScrollBean item) {
        ScrollBean.SAASOrderBean t = item.t;
        helper.setText(R.id.goods_name, t.getGoods_name());
        helper.setText(R.id.goods_price, t.getOrder_price());
        helper.setText(R.id.tv_number, t.getQuantity()+"");
        helper.setText(R.id.initial_order, "1份起订");

        KLog.d("image:"+t.getGoods_img());

        Glide.with(mContext).load(t.getGoods_img()).placeholder(R.mipmap.loading).into((ImageView) helper.getView(R.id.goods_image));


        EditText ss=helper.getView(R.id.tv_number);


        TextWatcher watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e(TAG, "beforeTextChanged: "+"输入前"+s.toString() );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e(TAG, "beforeTextChanged: "+"输入中"+s.toString() );
            }

            @Override
            public void afterTextChanged(Editable s) {
                KLog.a("beforeTextChanged: "+"输入后"+s.toString() );
                String value = s.toString();


                if ("".equals(value)){
                    item.t.setQuantity(0);
                    ss.setText("0");
                }else {
                    item.t.setQuantity(Integer.parseInt(value));
                }

                if (mEditListener != null) {
                    mEditListener.onEdit(item);
                }
            }
        };

//        ss.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction()==MotionEvent.ACTION_DOWN){
//
//                    ss.addTextChangedListener(watcher);
//
//                }
//                return false;
//            }
//        });

        ss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText mV = (EditText) v;
                if (hasFocus) {
                    mV.addTextChangedListener(watcher);
                } else {
                    mV.removeTextChangedListener(watcher);
                }
            }
        });



        helper.setOnClickListener(R.id.iv_edit_subtract, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.a("111");
                ss.removeTextChangedListener(watcher);
                if (item.t.getQuantity()==0){
                    RxToast.normal("不能再减了");
                }else {
                    item.t.setQuantity(item.t.getQuantity()-1);
                    /**
                     * 减操作
                     */
                    if (mChangeListener != null) {
                        mChangeListener.onChange(item);
                    }

                }

            }
        });
        helper.setOnClickListener(R.id.iv_edit_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.a("222");
                ss.removeTextChangedListener(watcher);
                    item.t.setQuantity(item.t.getQuantity()+1);
                    /**
                     * 加操作
                     */
                    if (mChangeListener != null) {
                        mChangeListener.onChange(item);
                    }



            }
        });

    }
    //加减的监听的回调
    public interface OnChangeListener {
        void onChange(ScrollBean lists);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    private OnChangeListener mChangeListener;


    //加减的监听的回调
    public interface OnEditListener {
        void onEdit(ScrollBean lists);
    }

    public void setOnEditListener(OnEditListener listener) {
        mEditListener = listener;
    }

    private OnEditListener mEditListener;

}
