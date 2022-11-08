package com.youwu.shopowner.ui.goods_operate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.fragment.bean.GroupBean;

import java.util.List;


public class GoodsSetUpAdapter extends RecyclerView.Adapter<GoodsSetUpAdapter.myViewHodler> {
    private Context context;
    private List<GroupBean> goodsEntityList;
    private int currentIndex = 0;

    //创建构造函数
    public GoodsSetUpAdapter(Context context, List<GroupBean> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.scroll_left, null);
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
//        StoreBean.DataBean data = goodsEntityList.get(position);
//        holder.mItemGoodsImg;
        holder.text_name.setText(goodsEntityList.get(position).getName());//获取实体类中的name字段并设置
        holder.bindData(goodsEntityList.get(position), position, currentIndex);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentIndex(position);

                /**
                 * 点击操作
                 */
                if (mScanningListener != null) {
                    mScanningListener.onScanning(goodsEntityList.get(position));
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

        private TextView text_name;
        private RelativeLayout mLayout_all;



        public myViewHodler(View itemView) {
            super(itemView);

            text_name = (TextView) itemView.findViewById(R.id.left_text);

            mLayout_all=(RelativeLayout) itemView.findViewById(R.id.layout_all);


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


        public void bindData(GroupBean goodsEntity, int position, int currentIndex) {
            if (position == currentIndex) {
                mLayout_all.setBackgroundResource(R.drawable.radius_ban_blue);

                text_name.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.main_white));
            } else {
                mLayout_all.setBackgroundResource(R.color.white);
                text_name.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.main_black));
            }
//            mItemGoodsName.setText(();
        }
    }

    //回调
    public interface OnScanningListener {
        void onScanning(GroupBean groupBean);
    }

    public void setOnScanningListener(OnScanningListener listener) {
        mScanningListener = listener;
    }

    private OnScanningListener mScanningListener;



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
        public void OnItemClick(View view, GroupBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}