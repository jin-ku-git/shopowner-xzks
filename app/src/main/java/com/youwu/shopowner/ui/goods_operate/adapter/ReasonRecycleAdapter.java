package com.youwu.shopowner.ui.goods_operate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.fragment.bean.ReasonBean;


import java.util.List;

/**
 * 报损原因适配器
 */
public class ReasonRecycleAdapter extends RecyclerView.Adapter<ReasonRecycleAdapter.myViewHodler> {
    private Context context;
    private List<ReasonBean> goodsEntityList;
    private int currentIndex = 0;

    //创建构造函数
    public ReasonRecycleAdapter(Context context, List<ReasonBean> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.item_reason_layout, null);
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

        holder.bindData(goodsEntityList.get(position), position, currentIndex);
        //根据点击位置绑定数据
        final ReasonBean data = goodsEntityList.get(position);
//        holder.mItemGoodsImg;
        holder.reason_name.setText(data.getName());//获取实体类中的name字段并设置



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentIndex(position);

                /**
                 * 点击操作
                 */
                if (mreasonListener != null) {
                    mreasonListener.onreason(goodsEntityList.get(position));
                }
            }
        });

    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        notifyDataSetChanged();
    }

    //回调
    public interface OnreasonListener {
        void onreason(ReasonBean reasonBean);
    }

    public void setOnreasonListener(OnreasonListener listener) {
        mreasonListener = listener;
    }

    private OnreasonListener mreasonListener;

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

        private TextView reason_name;//
        private ImageView imageView;





        public myViewHodler(View itemView) {
            super(itemView);

            reason_name = (TextView) itemView.findViewById(R.id.reason_name);
            imageView = itemView.findViewById(R.id.image);



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
        public void bindData(ReasonBean goodsEntity, int position, int currentIndex) {
            if (position == currentIndex) {

                imageView.setImageResource(R.mipmap.checked_iv);
            } else {

                imageView.setImageResource(R.mipmap.unchecked_iv);
            }
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
        public void OnItemClick(View view, ReasonBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}