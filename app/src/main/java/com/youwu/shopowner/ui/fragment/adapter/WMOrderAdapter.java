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
 * 小程序订单订货适配器
 */
public class WMOrderAdapter extends RecyclerView.Adapter<WMOrderAdapter.myViewHodler> {
    private Context context;
    private List<XXCOrderBean> mList;
    private int currentIndex = 0;

    //创建构造函数
    public WMOrderAdapter(Context context, List<XXCOrderBean> goodsEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.mList = goodsEntityList;//实体类数据ArrayList
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
        View itemView = View.inflate(context, R.layout.item_wm_order_layout, null);
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
        XXCOrderBean data = mList.get(position);


        holder.order_state_name.setText(data.getOrder_sn_name());

        holder.member_info.setText(data.getMember_info()+"\n"+data.getTime_msg());


          /*
         1.把内部RecyclerView的adapter和集合数据通过holder缓存
         2.使内部RecyclerView的adapter提供设置position的方法
         */
        holder.list.clear();
        holder.list.addAll(mList.get(position).getGoods_list());

        if (mList.get(position).getOrder_status()==3){
            holder.receivingLayout.setVisibility(View.GONE);
        }else {
            if (mList.get(position).getOrder_status()==2){
                holder.DiningOut.setVisibility(View.VISIBLE);
                holder.OrderReceiving.setVisibility(View.GONE);
                holder.OrderRefusal.setVisibility(View.GONE);
            }
            holder.refundLayout.setVisibility(View.GONE);
        }

        if (mList.get(position).getOrder_status()==3){
            holder.RefundReason.setText("退款原因："+data.getOrder_refund_data().getOrder_refund_reason());
            holder.RefundDescription.setText("退款描述："+data.getOrder_refund_data().getOrder_refund_mark());
            holder.order_refund_type_name.setText(data.getOrder_refund_data().getOrder_refund_type_name());
            holder.RefundAmount.setText(data.getPay_amount()+"");
            if (holder.mRvAdapter == null) {
                holder.mRvAdapter = new CommodityAdapter(context, holder.list, position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.ItemRefundRecycle.setLayoutManager(layoutManage);
                holder.ItemRefundRecycle.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                holder.ItemRefundRecycle.setAdapter(holder.mRvAdapter);

            } else {
                holder.mRvAdapter.setPosition(position);
                holder.mRvAdapter.notifyDataSetChanged();
            }

            //图片列表
            if (holder.refundImageAdapter == null) {
                holder.refundImageAdapter = new RefundImageAdapter(context, data.getOrder_refund_data().getOrder_refund_img(), position);
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
        }else {
            holder.total_amount.setText(data.getTotal_amount()+"");


            if (holder.mRvAdapter == null) {
                holder.mRvAdapter = new CommodityAdapter(context, holder.list, position);
                GridLayoutManager layoutManage = new GridLayoutManager(context, 1);
                holder.rvItemItem.setLayoutManager(layoutManage);
                holder.rvItemItem.addItemDecoration(new GridSpacingItemDecoration(1, 0, false));
                holder.rvItemItem.setAdapter(holder.mRvAdapter);

            } else {
                holder.mRvAdapter.setPosition(position);
                holder.mRvAdapter.notifyDataSetChanged();
            }
        }



        //接单
        holder.OrderReceiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onClick(mList.get(position),position,2);
                }

            }
        });
        //拒单
        holder.OrderRefusal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onClick(mList.get(position),position,3);
                }

            }
        });
        //出餐
        holder.DiningOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClickListener!=null){
                    mClickListener.onClick(mList.get(position),position,4);
                }

            }
        });
        //拒绝
        holder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectionStatusClickListener!=null){
                    mSelectionStatusClickListener.SelectionStatusonClick(mList.get(position),position,2);
                }
            }
        });
        //同意
        holder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectionStatusClickListener!=null){
                    mSelectionStatusClickListener.SelectionStatusonClick(mList.get(position),position,1);
                }
            }
        });

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
        return mList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {


        private View mView;
        private TextView order_state_name;//订单状态名称和订单号
        private TextView member_info;//用户名和手机号
        private TextView total_amount;//总价

        RecyclerView rvItemItem;//
        RecyclerView ItemRefundRecycle;//退款商品
        RecyclerView RefundImageRecycle;//图片

        LinearLayout mdianpu_biaoti;

        LinearLayout receivingLayout,refundLayout;

        TextView OrderReceiving;//接单
        TextView OrderRefusal;//拒单
        TextView DiningOut;//出餐
        TextView refuse;//拒绝
        TextView agree;//同意
        TextView RefundReason;//退款原因
        TextView RefundDescription;//退款描述
        TextView RefundAmount;//退款金额
        TextView order_refund_type_name;//退款金额


        private CommodityAdapter mRvAdapter;
        private List<XXCOrderBean.GoodsListBean> list = new ArrayList<>();

        private RefundImageAdapter refundImageAdapter;

        public myViewHodler(View itemView) {
            super(itemView);


            rvItemItem = itemView.findViewById(R.id.CommodityRecycle);
            ItemRefundRecycle = itemView.findViewById(R.id.RefundRecycle);
            RefundImageRecycle = itemView.findViewById(R.id.RefundImageRecycle);
            receivingLayout = itemView.findViewById(R.id.receivingLayout);
            refundLayout = itemView.findViewById(R.id.refundLayout);
            order_state_name = itemView.findViewById(R.id.order_state_name);
            member_info = itemView.findViewById(R.id.member_info);
            total_amount = itemView.findViewById(R.id.total_amount);

            RefundReason = itemView.findViewById(R.id.RefundReason);
            RefundDescription = itemView.findViewById(R.id.RefundDescription);
            RefundAmount = itemView.findViewById(R.id.RefundAmount);
            order_refund_type_name = itemView.findViewById(R.id.order_refund_type_name);

            mView = (View) itemView.findViewById(R.id.view);


            OrderReceiving = itemView.findViewById(R.id.OrderReceiving);
            OrderRefusal = itemView.findViewById(R.id.OrderRefusal);
            DiningOut = itemView.findViewById(R.id.DiningOut);
            refuse = itemView.findViewById(R.id.refuse);
            agree = itemView.findViewById(R.id.agree);



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


    //按钮的监听的回调
    public interface OnClickListener {
        void onClick(XXCOrderBean lists, int position, int status);
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }

    private OnClickListener mClickListener;


    //按钮的监听的回调
    public interface SelectionStatusOnClickListener {
        void SelectionStatusonClick(XXCOrderBean lists, int position, int status);
    }

    public void setSelectionStatusOnClickListener(SelectionStatusOnClickListener listener) {
        mSelectionStatusClickListener = listener;
    }

    private SelectionStatusOnClickListener mSelectionStatusClickListener;


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
        public void OnItemClick(View view, XXCOrderBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}