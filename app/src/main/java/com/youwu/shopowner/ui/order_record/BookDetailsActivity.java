package com.youwu.shopowner.ui.order_record;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityBookDetailsBinding;
import com.youwu.shopowner.databinding.ActivityLossReportingDetailsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.adapter.LossGoodsRecycleAdapter;
import com.youwu.shopowner.ui.fragment.bean.OrderDetailsBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.ui.goods_operate.LossReportingDetailsViewModel;
import com.youwu.shopowner.ui.main.MainActivity;
import com.youwu.shopowner.ui.order_goods.adapter.OrderGoodsRecycleAdapter;
import com.youwu.shopowner.ui.order_record.adapder.ReceivingAdapter;
import com.youwu.shopowner.ui.order_record.bean.OrderGoodsBean;
import com.youwu.shopowner.utils_view.BigDecimalUtils;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 订货详情页面
 * @author: Administrator
 * @date: 2022/9/16
 */
public class BookDetailsActivity extends BaseActivity<ActivityBookDetailsBinding, BookDetailsViewModel> {




    OrderGoodsBean orderGoodsBean;

    /**
     * 收货数据
     */
    private ReceivingAdapter mReceivingAdapter;
    //收货数据
    private List<OrderGoodsBean.DetailsBean> mReceivingBeanList = new ArrayList<>();

    String store_id;//门店id
    @Override
    public void initParam() {
        super.initParam();

        orderGoodsBean= (OrderGoodsBean) getIntent().getSerializableExtra("OrderGoodsBean");
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_book_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public BookDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(BookDetailsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://收货

                        if (orderGoodsBean.getStatus()!=3){
                            orderGoodsBean.setDetails(mReceivingBeanList);
                            String submitJson = new Gson().toJson(orderGoodsBean);
                            KLog.d("收货订单解析数据："+submitJson);

                            viewModel.Receiving(submitJson);
                        }else {
                            finish();
                            Bundle bundle=new Bundle();
                            bundle.putInt("type",2);
                            bundle.putSerializable("OrderGoodsBean",orderGoodsBean);
                            startActivity(MainActivity.class,bundle);
                        }


                        break;

                    case 3://订货成功
                        RxToast.showTipToast(BookDetailsActivity.this, "订货成功");
                        break;

                }
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
        viewModel.TotalPrice.set(orderGoodsBean.getTotal_price()+"");
        viewModel.ReceiptTime.set(orderGoodsBean.getArrival_time()+"");
        viewModel.created_at.set(orderGoodsBean.getCreated_at()+"");
        if (orderGoodsBean.getDetails()!=null){
            viewModel.TotalType.set(orderGoodsBean.getDetails().size()+"");
        }
        viewModel.TotalQuantity.set(orderGoodsBean.getTotal_quantity()+"");
        viewModel.OrderState.set(orderGoodsBean.getStatus_name()+"");

        viewModel.status.set(orderGoodsBean.getStatus());

        mReceivingBeanList.addAll(orderGoodsBean.getDetails());

        if (orderGoodsBean.getStatus()!=3){
            for (int i=0;i<mReceivingBeanList.size();i++){
                mReceivingBeanList.get(i).setActual_quantity(mReceivingBeanList.get(i).getOrder_quantity()+"");
            }
        }



        initRecyclerView();

        //检查是否已经注册
        if(!EventBus.getDefault().isRegistered(this)){//是否注册eventbus的判断
            EventBus.getDefault().register(this);
        }
    }

    //获取从ReceivingAdapter传递的值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ReceivingEvbus(List<OrderGoodsBean.DetailsBean> itemListBeans) {
        KLog.d("1111111111："+itemListBeans.size());


        mReceivingBeanList=itemListBeans;
        String submitJson = new Gson().toJson(mReceivingBeanList);
        KLog.d("收货订单解析数据："+submitJson);

    }

    /**
     * 购物车列表
     */
    private void initRecyclerView() {


        //创建adapter
        mReceivingAdapter = new ReceivingAdapter(this, mReceivingBeanList,orderGoodsBean.getStatus());
        //给RecyclerView设置adapter
        binding.goodsRecyclerview.setAdapter(mReceivingAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecyclerview.getItemDecorationCount()==0) {
            binding.goodsRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
