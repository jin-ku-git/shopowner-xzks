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
import com.youwu.shopowner.ui.order_record.bean.RecordBean;

import java.util.List;

import static me.goldze.mvvmhabit.base.BaseActivity.subZeroAndDot;

/**
 * 记录适配器
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.myViewHodler> {
    private Context context;
    private List<RecordBean.RowsBean> rechargeBeans;
    private int currentIndex = 0;
    private int type = 0;


    //创建构造函数
    public RecordAdapter(Context context, List<RecordBean.RowsBean> goodsEntityList,int type) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.rechargeBeans = goodsEntityList;//实体类数据ArrayList
        this.type = type;

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
        View itemView = View.inflate(context, R.layout.item_record_layout, null);
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
        RecordBean.RowsBean data = rechargeBeans.get(position);


        if (type==1){
            holder.time_name.setText("报损时间");
            holder.user_name.setText("报损人员");
            holder.goods_type.setText("报损商品种类");
            holder.remarks_name.setText("报损备注");
            holder.type.setText(data.getTotal_kind_quantity()+"");
        }else if (type==2){
            holder.time_name.setText("盘点时间");
            holder.user_name.setText("盘点人员");
            holder.goods_type.setText("盘点商品种类");
            holder.remarks_name.setText("盘点备注");
            holder.type.setText(data.getTotal()+"");
        }else if (type==3){
            holder.time_name.setText("沽清时间");
            holder.user_name.setText("沽清人员");
            holder.goods_type.setText("沽清商品种类");
            holder.remarks_name.setText("沽清备注");
            holder.type.setText(data.getTotal()+"");
        }
        holder.time.setText(data.getCreated_at());
        holder.name.setText(data.getCashier_name());

        holder.remarks.setText(data.getMark()+"");



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mClickListener!=null){
                    mClickListener.onClick(rechargeBeans.get(position),position);
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
        return rechargeBeans.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {

        private TextView time_name,time;//
        private TextView user_name,name;//商品数量
        private TextView goods_type,type;
        private TextView remarks_name,remarks;

        public myViewHodler(View itemView) {
            super(itemView);

            time_name = (TextView) itemView.findViewById(R.id.time_name);
            time = (TextView) itemView.findViewById(R.id.time);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            name = (TextView) itemView.findViewById(R.id.name);

            goods_type = (TextView) itemView.findViewById(R.id.goods_type);
            type = (TextView) itemView.findViewById(R.id.type);
            remarks_name = (TextView) itemView.findViewById(R.id.remarks_name);
            remarks = (TextView) itemView.findViewById(R.id.remarks);



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


    }


    //删除的监听的回调
    public interface OnClickListener {
        void onClick(RecordBean.RowsBean lists, int position);
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
        public void OnItemClick(View view, RecordBean.RowsBean data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}