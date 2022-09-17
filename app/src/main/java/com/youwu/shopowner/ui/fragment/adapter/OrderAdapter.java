package com.youwu.shopowner.ui.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner.utils_view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 门店订单适配器
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.myViewHodler> {
    private Context context;
    private List<String> mList;


    //创建构造函数
    public OrderAdapter(Context context, List<String> orderEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mList = orderEntityList;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.item_order_layout, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final myViewHodler holder, @SuppressLint("RecyclerView") final int position) {

        //根据点击位置绑定数据
        String data = mList.get(position);



            //图片列表
            if (holder.refundImageAdapter == null) {
                holder.refundImageAdapter = new ImageAdapter(context, mList, position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.RefundImageRecycle.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
                if (holder.RefundImageRecycle.getItemDecorationCount()==0) {
                    holder.RefundImageRecycle.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                }
                holder.RefundImageRecycle.setAdapter(holder.refundImageAdapter);

            } else {
                holder.refundImageAdapter.setPosition(position);
                holder.refundImageAdapter.notifyDataSetChanged();
            }



    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {



        RecyclerView RefundImageRecycle;//图片


        private ImageAdapter refundImageAdapter;

        public myViewHodler(View itemView) {
            super(itemView);



            RefundImageRecycle = itemView.findViewById(R.id.RefundImageRecycle);


            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理

                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, mList.get(getLayoutPosition()),getLayoutPosition());
                    }
                }
            });
        }


    }




    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, String data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}