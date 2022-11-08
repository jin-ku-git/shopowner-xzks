package com.youwu.shopowner.ui.fragment.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youwu.shopowner.R;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 2022/11/02
 */

public class GoodsSetUpRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {

    public GoodsSetUpRightAdapter(int layoutResId, int sectionHeadResId, List<ScrollBean> data) {
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


        //修改
        helper.setOnClickListener(R.id.SetUP, new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mChangeListener != null) {
                    mChangeListener.onChange(item,3);
                }
            }
        });
        //上架
        helper.setOnClickListener(R.id.PutShelves, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (mChangeListener != null) {
                        mChangeListener.onChange(item,1);
                    }


            }
        });
        //下架
        helper.setOnClickListener(R.id.LowerShelf, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (mChangeListener != null) {
                        mChangeListener.onChange(item,2);
                    }



            }
        });

    }
    //上下架的监听的回调
    public interface OnChangeListener {
        void onChange(ScrollBean lists,int type);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    private OnChangeListener mChangeListener;

    //报损原因的监听的回调
    public interface OnReasonListener {
        void onReason(ScrollBean lists);
    }

    public void setOnReasonListener(OnReasonListener listener) {
        mReasonListener = listener;
    }

    private OnReasonListener mReasonListener;
}
