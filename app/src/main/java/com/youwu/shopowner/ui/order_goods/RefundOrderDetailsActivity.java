package com.youwu.shopowner.ui.order_goods;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityOrderDetailsBinding;
import com.youwu.shopowner.databinding.ActivityRefundOrderDetailsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.OrderDetailsBean;
import com.youwu.shopowner.ui.fragment.bean.RefundDetailsBean;
import com.youwu.shopowner.ui.fragment.bean.SaleBillBean;
import com.youwu.shopowner.ui.order_goods.adapter.OrderGoodsRecycleAdapter;
import com.youwu.shopowner.ui.order_goods.adapter.RefundImageRecycleAdapter;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @author: Administrator
 * @date: 2022/9/17
 */
public class RefundOrderDetailsActivity extends BaseActivity<ActivityRefundOrderDetailsBinding, RefundOrderDetailsViewModel> {


    //recyclerveiw的适配器
    private OrderGoodsRecycleAdapter mOrderGoodsRecycleAdapter;
    private RefundImageRecycleAdapter mRefundImageRecycleAdapter;
    //数据集合
    private ArrayList<OrderDetailsBean.GoodsListBean> mList = new ArrayList<>();
    private ArrayList<String> mImageList = new ArrayList<>();


    String order_sn;
    String store_id;
    @Override
    public void initParam() {
        super.initParam();
        order_sn=getIntent().getStringExtra("order_sn");
    }
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_refund_order_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RefundOrderDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RefundOrderDetailsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        initPrint();
                        break;

                }
            }
        });

        viewModel.OrderDetailsLiveEvent.observe(this, new Observer<RefundDetailsBean>() {
            @Override
            public void onChanged(RefundDetailsBean orderDetailsBean) {

                for (int i=0;i<orderDetailsBean.getDetails().size();i++){
                    OrderDetailsBean.GoodsListBean goodsListBean=new OrderDetailsBean.GoodsListBean();
                    goodsListBean.setGoods_name(orderDetailsBean.getDetails().get(i).getGoods_name());
                    goodsListBean.setGoods_price(orderDetailsBean.getDetails().get(i).getGoods_price());
                    goodsListBean.setGoods_number(orderDetailsBean.getDetails().get(i).getGoods_number());
                    goodsListBean.setGoods_thumb(orderDetailsBean.getDetails().get(i).getGoods_thumb());
                    mList.add(goodsListBean);
                }

                mImageList.addAll(orderDetailsBean.getImage());

                initRecyclerView();
                initImageRecyclerView();
            }
        });
    }

    /**
     * 打印
     */
    private void initPrint() {
        viewModel.Print(order_sn,store_id);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        store_id= AppApplication.spUtils.getString("StoreId");
        initOrderDetails();

    }

    /**
     * 查询订单详情
     */
    private void initOrderDetails() {
        viewModel.refund_details(order_sn);
    }

    /**
     * 商品列表
     */
    private void initRecyclerView() {
        //创建adapter
        mOrderGoodsRecycleAdapter = new OrderGoodsRecycleAdapter(this, mList);
        //给RecyclerView设置adapter
        binding.goodsRecyclerview.setAdapter(mOrderGoodsRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecyclerview.getItemDecorationCount() == 0) {
            binding.goodsRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
    }

    /**
     * 图片列表
     */
    private void initImageRecyclerView() {
        if (mImageList.size()==0){
            binding.refundImageLayout.setVisibility(View.GONE);
        }else {
            binding.refundImageLayout.setVisibility(View.VISIBLE);
        }
        //创建adapter
        mRefundImageRecycleAdapter = new RefundImageRecycleAdapter(this, mImageList);
        //给RecyclerView设置adapter
        binding.imageRecyclerview.setAdapter(mRefundImageRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.imageRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        //设置item的分割线
        if (binding.imageRecyclerview.getItemDecorationCount() == 0) {
            binding.imageRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.HORIZONTAL));
        }
    }
}
