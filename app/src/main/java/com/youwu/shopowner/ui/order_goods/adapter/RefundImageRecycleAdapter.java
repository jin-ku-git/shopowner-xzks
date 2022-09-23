package com.youwu.shopowner.ui.order_goods.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.youwu.shopowner.R;
import com.youwu.shopowner.ui.fragment.bean.OrderDetailsBean;
import com.youwu.shopowner.ui.fragment.bean.SaleBillBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.goldze.mvvmhabit.http.NetworkUtil.url;


public class RefundImageRecycleAdapter extends RecyclerView.Adapter<RefundImageRecycleAdapter.myViewHodler> {
    private Context context;
    private List<String> goodsEntityList;
    private int currentIndex = 0;

    //创建构造函数
    public RefundImageRecycleAdapter(Context context, List<String> goodsEntityList) {
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
        View itemView = View.inflate(context, R.layout.item_refund_image_layout, null);
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
        final String data = goodsEntityList.get(position);

        Glide.with(context).load(data).placeholder(R.mipmap.loading).into(holder.goods_image);

        List<Object> list= new ArrayList<>();
        list.addAll(goodsEntityList);

        holder.goods_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new XPopup.Builder(context).asImageViewer(holder.goods_image, position, list, new OnSrcViewUpdateListener() {
                           @Override
                           public void onSrcViewUpdate(@NonNull ImageViewerPopupView popupView, int position) {
                               popupView.updateSrcView(holder.goods_image);
                           }
                       }, new ImageLoader())
                    .show();
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


        private ImageView goods_image;//图片





        public myViewHodler(View itemView) {
            super(itemView);

            goods_image =  itemView.findViewById(R.id.goods_image);




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
        public void OnItemClick(View view, String data, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public class  ImageLoader implements XPopupImageLoader {

        //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.loading).override(Target.SIZE_ORIGINAL)).into(imageView);
//            Glide.with(imageView).load(url).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e ) {
                e.printStackTrace();
            }
            return null;
        }
    };
}
