package com.youwu.shopowner.ui.order_goods;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityOrderDetailsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.adapter.ShoppingRecycleAdapter;
import com.youwu.shopowner.ui.fragment.bean.OrderDetailsBean;
import com.youwu.shopowner.ui.fragment.bean.SaleBillBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.ui.order_goods.adapter.OrderGoodsRecycleAdapter;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;


import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 订单详情
 * @author: Administrator
 * @date: 2022/9/17
 */
public class OrderDetailsActivity extends BaseActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel> {


    //recyclerveiw的适配器
    private OrderGoodsRecycleAdapter mOrderGoodsRecycleAdapter;
    //数据集合
    private ArrayList<OrderDetailsBean.GoodsListBean> mList = new ArrayList<>();

    String order_sn;
    String store_id;
    @Override
    public void initParam() {
        super.initParam();
        order_sn=getIntent().getStringExtra("order_sn");
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public OrderDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(OrderDetailsViewModel.class);
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
        viewModel.OrderDetailsLiveEvent.observe(this, new Observer<OrderDetailsBean>() {
            @Override
            public void onChanged(OrderDetailsBean orderDetailsBean) {

                mList.addAll(orderDetailsBean.getGoods_list());

                initRecyclerView();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        store_id= AppApplication.spUtils.getString("StoreId");
        KLog.d("order_sn:"+order_sn);
        initOrderDetails();
    }

    /**
     * 打印
     */
    private void initPrint() {
        viewModel.Print(order_sn,store_id);
    }
    /**
     * 查询订单详情
     */
    private void initOrderDetails() {
        viewModel.order_details(order_sn);
    }

    /**
     * 商品列表
     */
    private void initRecyclerView() {
        //创建adapter
        mOrderGoodsRecycleAdapter = new OrderGoodsRecycleAdapter(this, mList);
        //给RecyclerView设置adapter
        binding.GoodsRecyclerview.setAdapter(mOrderGoodsRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.GoodsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.GoodsRecyclerview.getItemDecorationCount() == 0) {
            binding.GoodsRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
    }
}
