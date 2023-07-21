package com.youwu.shopowner.ui.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.youwu.shopowner.R;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.utils_view.BigDecimalUtils;

import java.util.List;


public class ShoppingRecycleAdapter extends RecyclerView.Adapter<ShoppingRecycleAdapter.myViewHodler> {
    private Context context;
    private List<ScrollBean.SAASOrderBean> goodsEntityList;
    private int currentIndex = 0;

    //创建构造函数
    public ShoppingRecycleAdapter(Context context, List<ScrollBean.SAASOrderBean> goodsEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.goodsEntityList = goodsEntityList;//实体类数据ArrayList
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
        View itemView = View.inflate(context, R.layout.item_shopping_layout, null);
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
        final ScrollBean.SAASOrderBean data = goodsEntityList.get(position);
//        holder.mItemGoodsImg;
        holder.goods_name.setText(data.getGoods_name());//获取实体类中的name字段并设置
//        String price= BigDecimalUtils.formatRoundUp((Double.parseDouble(data.getOrder_price())*data.getQuantity()),2)+"";

        holder.goods_price.setText(data.getOrder_price());//获取实体类中的name字段并设置
        holder.tv_number.setText(data.getQuantity()+"");

        Glide.with(context).load(data.getGoods_img()).placeholder(R.mipmap.loading).into(holder.goods_image);


        holder.iv_edit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    data.setQuantity(data.getQuantity()+1);
                    data.setOrder_quantity(data.getOrder_quantity()+1);

                    /**
                     * 加操作
                     */
                    if (mChangeListener != null) {
                        mChangeListener.onChange(data,position);
                    }
                    notifyDataSetChanged();


            }
        });
        holder.iv_edit_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getQuantity()==1){
                    /**
                     * 删除操作
                     */
                    if (mDeleteListener != null) {
                        mDeleteListener.onDelete(data,position);
                    }
                }else {
                    data.setQuantity(data.getQuantity()-1);
                    data.setOrder_quantity(data.getOrder_quantity()-1);

                    /**
                     * 减操作
                     */
                    if (mChangeListener != null) {
                        mChangeListener.onChange(data,position);
                    }
                }
                notifyDataSetChanged();

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
        return goodsEntityList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {

        private TextView goods_name,goods_price;//商品名称，商品价格
        private TextView tv_number;//商品数量

        private ImageView iv_edit_add,iv_edit_subtract;//加  减

        private ImageView goods_image;




        public myViewHodler(View itemView) {
            super(itemView);

            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            iv_edit_add = (ImageView) itemView.findViewById(R.id.iv_edit_add);
            iv_edit_subtract = (ImageView) itemView.findViewById(R.id.iv_edit_subtract);
            goods_image = (ImageView) itemView.findViewById(R.id.goods_image);


            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, goodsEntityList.get(getLayoutPosition()),getLayoutPosition());
                    }
                }
            });


        }

    }


    //加减的监听的回调
    public interface OnChangeListener {
        void onChange(ScrollBean.SAASOrderBean lists, int position);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    private OnChangeListener mChangeListener;

    //删除的监听的回调
    public interface OnDeleteListener {
        void onDelete(ScrollBean.SAASOrderBean lists, int position);
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        mDeleteListener = listener;
    }

    private OnDeleteListener mDeleteListener;



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
        public void OnItemClick(View view, ScrollBean.SAASOrderBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}