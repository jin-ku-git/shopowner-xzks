package com.youwu.shopowner.ui.fragment.adapter;


import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.fragment.bean.GroupBean;

import java.util.ArrayList;
import java.util.List;

import static com.xuexiang.xui.utils.ResUtils.getResources;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class ScrollLeftAdapter extends BaseQuickAdapter<GroupBean, BaseViewHolder> {

    private List<TextView> tv = new ArrayList<>();
    private List<RelativeLayout> layout_all = new ArrayList<>();
    public ScrollLeftAdapter(int layoutResId, @Nullable List<GroupBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupBean item) {
        helper.setText(R.id.left_text, item.getName())
                .addOnClickListener(R.id.item);
        //将左侧item中的TextView添加到集合中
        tv.add((TextView) helper.getView(R.id.left_text));
        layout_all.add((RelativeLayout) helper.getView(R.id.layout_all));
        //设置进入页面之后,左边列表的初始状态
        if (tv != null && getData() != null && tv.size() == getData().size()) {
            selectItem(0);
        }

        helper.getView(R.id.item).setSelected(true);
    }

    //传入position,设置左侧列表相应item高亮
    public void selectItem(int position) {
        for (int i = 0; i < getData().size(); i++) {
            if (position == i) {
                layout_all.get(i).setBackgroundResource(R.drawable.radius_ban_blue);
                tv.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.white));

                //以下是指定某一个TextView滚动的效果
                tv.get(i).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tv.get(i).setFocusable(true);
                tv.get(i).setFocusableInTouchMode(true);
                tv.get(i).setMarqueeRepeatLimit(-1);
            } else {
                tv.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.black));
                layout_all.get(i).setBackgroundResource(R.color.main_touming);
                //失去焦点则停止滚动
                tv.get(i).setEllipsize(TextUtils.TruncateAt.END);
                tv.get(i).setFocusable(false);
                tv.get(i).setFocusableInTouchMode(false);
                tv.get(i).setMarqueeRepeatLimit(0);
            }
        }
    }
}
