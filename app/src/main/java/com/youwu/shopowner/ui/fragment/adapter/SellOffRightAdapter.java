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

/**
 * Created by Raul_lsj on 2018/3/28.
 */

public class SellOffRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {

    public SellOffRightAdapter(int layoutResId, int sectionHeadResId, List<ScrollBean> data) {
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

        helper.setImageResource(R.id.store_unchecked_img,t.getQuantity()==1?R.mipmap.checked_iv:R.mipmap.unchecked_iv);

        Glide.with(mContext).load(t.getGoods_img()).placeholder(R.mipmap.loading).into((ImageView) helper.getView(R.id.goods_image));
        helper.setOnClickListener(R.id.layout_all, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.t.getQuantity()==0){
                    item.t.setQuantity(item.t.getQuantity()+1);
                    /**
                     * 减操作
                     */
                    if (mChangeListener != null) {
                        mChangeListener.onChange(item);
                    }
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


    }
    //加减的监听的回调
    public interface OnChangeListener {
        void onChange(ScrollBean lists);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    private OnChangeListener mChangeListener;
}
