package com.youwu.shopowner.ui.order_record.adapder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.order_record.bean.OrderGoodsBean;

import java.util.List;

import static me.goldze.mvvmhabit.base.BaseActivity.subZeroAndDot;

/**
 * 订货申请适配器
 */
public class OrderGoodsAdapter extends RecyclerView.Adapter<OrderGoodsAdapter.myViewHodler> {
    private Context context;
    private List<OrderGoodsBean> rechargeBeans;
    private int currentIndex = 0;
    private int type;

    //创建构造函数
    public OrderGoodsAdapter(Context context, List<OrderGoodsBean> goodsEntityList,int type) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.rechargeBeans = goodsEntityList;//实体类数据ArrayList
        this.type=type;

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
        View itemView = View.inflate(context, R.layout.item_order_goods_list_layout, null);
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
        OrderGoodsBean data = rechargeBeans.get(position);


        if ("已签收".equals(data.getStatus_name())&&"已退货".equals(data.getAudit_name())){
            holder.order_state.setText("订单状态："+data.getAudit_name());//状态
        }else {
            holder.order_state.setText("订单状态："+data.getStatus_name());//状态
        }
        switch (data.getStatus()){
            case 1://已提交
                holder.order_state.setTextColor(ContextCompat.getColor(context, R.color.blue_color));
                break;
            case 2://已配货
                if (type==2){
                    holder.order_state.setTextColor(ContextCompat.getColor(context, R.color.main_green));
                }else {
                    holder.order_state.setTextColor(ContextCompat.getColor(context, R.color.main_yellow));
                }
                break;
            case 3://已签收
                if (type==2){//已拒绝
                    holder.order_state.setTextColor(ContextCompat.getColor(context, R.color.color_red));
                }else {//已签收
                    holder.order_state.setTextColor(ContextCompat.getColor(context, R.color.main_green));
                }

                break;
        }
        if (type==2){
            holder.order_time.setText("退货日期："+data.getCreated_at());//时间
        }else {
            holder.order_time.setText("订货日期："+data.getCreated_at());//时间
        }

        holder.goods_num.setText("共"+ subZeroAndDot(data.getTotal_quantity()+"")+"件");//数量




//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(mClickListener!=null){
//                    mClickListener.onClick(rechargeBeans.get(position),position);
//                }
//            }
//        });
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        notifyDataSetChanged();
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return rechargeBeans.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {

        private TextView order_state,order_time;//订单状态，订单时间
        private TextView goods_num;//商品数量
        private View mView;

        public myViewHodler(View itemView) {
            super(itemView);

            order_state = (TextView) itemView.findViewById(R.id.order_state);
            order_time = (TextView) itemView.findViewById(R.id.order_time);
            goods_num = (TextView) itemView.findViewById(R.id.goods_num);

            mView = (View) itemView.findViewById(R.id.view);



            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, rechargeBeans.get(getLayoutPosition()),getLayoutPosition());
                    }
                }
            });
        }

        public void bindData( int position, int currentIndex) {
            if (position == currentIndex) {

                mView.setVisibility(View.VISIBLE);
                goods_num.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.main_color));
            } else {

                mView.setVisibility(View.GONE);
                goods_num.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_hui_777));
            }
//            mItemGoodsName.setText(();
        }

    }


    //删除的监听的回调
    public interface OnClickListener {
        void onClick(OrderGoodsBean lists, int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    private OnClickListener mClickListener;



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
        public void OnItemClick(View view, OrderGoodsBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}