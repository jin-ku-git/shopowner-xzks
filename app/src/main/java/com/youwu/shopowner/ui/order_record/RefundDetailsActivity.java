package com.youwu.shopowner.ui.order_record;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityBookDetailsBinding;
import com.youwu.shopowner.databinding.ActivityRefundDetailsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.OrderDetailsBean;
import com.youwu.shopowner.ui.order_goods.adapter.OrderGoodsRecycleAdapter;
import com.youwu.shopowner.ui.order_record.adapder.ReceivingAdapter;
import com.youwu.shopowner.ui.order_record.bean.CargoRefundBean;
import com.youwu.shopowner.ui.order_record.bean.OrderGoodsBean;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 退货货详情页面
 * @author: Administrator
 * @date: 2022/9/16
 */
public class RefundDetailsActivity extends BaseActivity<ActivityRefundDetailsBinding, RefundDetailsViewModel> {


    String order_sn;

    /**
     * 退货数据
     */
    private OrderGoodsRecycleAdapter mReceivingAdapter;
    //收货数据
    private List<OrderDetailsBean.GoodsListBean> mReceivingBeanList = new ArrayList<>();

    String store_id;//门店id
    @Override
    public void initParam() {
        super.initParam();

        order_sn= getIntent().getStringExtra("order_sn");
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_refund_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public RefundDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RefundDetailsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://收货

                        break;

                    case 3://订货成功
                        RxToast.showTipToast(RefundDetailsActivity.this, "订货成功");
                        break;

                }
            }
        });
        viewModel.CargoRefundEvent.observe(this, new Observer<CargoRefundBean>() {
            @Override
            public void onChanged(CargoRefundBean cargoRefundBean) {

                for (int i=0;i<cargoRefundBean.getDetails().size();i++){
                    OrderDetailsBean.GoodsListBean detailsBean=new OrderDetailsBean.GoodsListBean();
                    detailsBean.setGoods_name(cargoRefundBean.getDetails().get(i).getGoods_name());
                    detailsBean.setGoods_price(cargoRefundBean.getDetails().get(i).getOrder_price()+"");
                    detailsBean.setGoods_number(cargoRefundBean.getDetails().get(i).getQuantity());
                    detailsBean.setGoods_thumb(cargoRefundBean.getDetails().get(i).getImage());

                    mReceivingBeanList.add(detailsBean);
                }
                initRecyclerView(cargoRefundBean.getStatus());
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

        viewModel.refund_details(order_sn);



    }

    /**
     * 购物车列表
     * @param status
     */
    private void initRecyclerView(int status) {


        //创建adapter
        mReceivingAdapter = new OrderGoodsRecycleAdapter(this, mReceivingBeanList);
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

}
