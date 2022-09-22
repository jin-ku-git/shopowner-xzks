package com.youwu.shopowner.ui.fragment.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youwu.shopowner.R;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;

import java.util.List;

/**
 * Created by Raul_lsj on 2018/3/28.
 */

public class InventoryRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {

    public InventoryRightAdapter(int layoutResId, int sectionHeadResId, List<ScrollBean> data) {
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
        helper.setText(R.id.goods_price, "原库存0份");
        helper.setText(R.id.tv_number, t.getQuantity()+"");



        helper.setOnClickListener(R.id.iv_edit_subtract, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
