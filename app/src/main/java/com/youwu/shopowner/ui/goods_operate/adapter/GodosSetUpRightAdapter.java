package com.youwu.shopowner.ui.goods_operate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.fragment.adapter.GoodsSetUpRightAdapter;
import com.youwu.shopowner.ui.fragment.bean.CommunityBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.utils_view.CustomRoundAngleImageView;


import org.w3c.dom.Text;

import java.util.List;


public class GodosSetUpRightAdapter extends RecyclerView.Adapter<GodosSetUpRightAdapter.myViewHodler> {
    private Context context;
    private List<CommunityBean> goodsEntityList;
    private int currentIndex = 0;

    //创建构造函数
    public GodosSetUpRightAdapter(Context context, List<CommunityBean> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.goods_set_up_right, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, @SuppressLint("RecyclerView") final int position) {

        //根据点击位置绑定数据
        final CommunityBean data = goodsEntityList.get(position);
//        holder.mItemGoodsImg;
        holder.goods_name.setText(data.getGoods_name());//获取实体类中的name字段并设置
        holder.goods_price.setText(data.getGoods_price());//获取实体类中的name字段并设置
        holder.StockQuantity.setText(data.getStock()+"");


        Glide.with(context).load(data.getGoods_img()).placeholder(R.mipmap.loading).into(holder.goods_image);

        if (data.getStatus()==1){//已上架
            holder.PutShelves.setVisibility(View.GONE);
            holder.LowerShelf.setVisibility(View.VISIBLE);
        }else {
            holder.PutShelves.setVisibility(View.VISIBLE);
            holder.LowerShelf.setVisibility(View.GONE);

        }

        //修改
        holder.SetUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mChangeListener != null) {
                    mChangeListener.onChange(data,3,position);
                }
            }
        });
        //上架
        holder.PutShelves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChangeListener != null) {
                    mChangeListener.onChange(data,1,position);
                }
            }
        });
        //下架
        holder.LowerShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChangeListener != null) {
                    mChangeListener.onChange(data,2,position);
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
        return goodsEntityList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {

        private TextView goods_name,goods_price;//商品名称,商品价格

        private CustomRoundAngleImageView goods_image;//商品图片

        private TextView SetUP,PutShelves,LowerShelf;
        private TextView StockQuantity;



        public myViewHodler(View itemView) {
            super(itemView);

            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);

            goods_image =  itemView.findViewById(R.id.goods_image);
            SetUP =  itemView.findViewById(R.id.SetUP);
            PutShelves =  itemView.findViewById(R.id.PutShelves);
            LowerShelf =  itemView.findViewById(R.id.LowerShelf);
            StockQuantity =  itemView.findViewById(R.id.StockQuantity);





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


    //上下架的监听的回调
    public interface OnChangeListener {
        void onChange(CommunityBean date, int type,int position);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mChangeListener = listener;
    }

    private OnChangeListener mChangeListener;



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
        public void OnItemClick(View view, CommunityBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}