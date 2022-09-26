package com.youwu.shopowner.ui.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;


public class InventoryRecycleAdapter extends RecyclerView.Adapter<InventoryRecycleAdapter.myViewHodler> {
    private Context context;
    private List<ScrollBean.SAASOrderBean> goodsEntityList;
    private int currentIndex = 0;

    //创建构造函数
    public InventoryRecycleAdapter(Context context, List<ScrollBean.SAASOrderBean> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.item_inventory_layout, null);
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

        holder.goods_price.setText("原库存"+data.getStock()+"份");//获取实体类中的name字段并设置
        holder.tv_number.setText(data.getChange_stock()+"");

        TextWatcher watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e(TAG, "beforeTextChanged: "+"输入前"+s.toString() );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e(TAG, "beforeTextChanged: "+"输入中"+s.toString() );
            }

            @Override
            public void afterTextChanged(Editable s) {
                KLog.d("beforeTextChanged: "+"输入后"+s.toString());

                String value = s.toString();

                if ("".equals(value)){
                    data.setChange_stock(0);
                    holder.tv_number.setText("0");
                }else {
                    data.setChange_stock(Integer.parseInt(value));
                }

                /**
                 * 减操作
                 */
                if (mEditListener != null) {
                    mEditListener.onEdit(data,position);
                }


            }
        };
        holder.tv_number.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    holder.tv_number.addTextChangedListener(watcher);
                }
                return false;
            }
        });


        holder.iv_edit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_number.removeTextChangedListener(watcher);
                data.setChange_stock(data.getChange_stock()+1);
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
                holder.tv_number.removeTextChangedListener(watcher);
                if (data.getChange_stock()==1){
                    /**
                     * 删除操作
                     */
                    if (mDeleteListener != null) {
                        mDeleteListener.onDelete(data,position);
                    }
                }else {
                    data.setChange_stock(data.getChange_stock()-1);
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




        public myViewHodler(View itemView) {
            super(itemView);

            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            goods_price = (TextView) itemView.findViewById(R.id.goods_price);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            iv_edit_add = (ImageView) itemView.findViewById(R.id.iv_edit_add);
            iv_edit_subtract = (ImageView) itemView.findViewById(R.id.iv_edit_subtract);


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


    //加减的监听的回调
    public interface OnEditListener {
        void onEdit(ScrollBean.SAASOrderBean lists,int position);
    }

    public void setOnEditListener(OnEditListener listener) {
        mEditListener = listener;
    }

    private OnEditListener mEditListener;


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