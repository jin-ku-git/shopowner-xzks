package com.youwu.shopowner.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.FragmentOneBinding;
import com.youwu.shopowner.ui.main.EventBusBean;
import com.youwu.shopowner.ui.order_goods.OrderReceivingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * 2022/09/12
 */

public class OneFragment extends BaseFragment<FragmentOneBinding,OneViewModel> {


    String  StoreId;//店铺id

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_one;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public OneViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(OneViewModel.class);
    }
    @Override
    public void onResume() {
        super.onResume();

        viewModel.getxcx_order_count();
    }

    @Override
    public void initData() {
        super.initData();
        /**
         * 检查更新
         */
        viewModel.getAppVersion();

        viewModel.getMe();
       StoreId= AppApplication.spUtils.getString("StoreId");
        viewModel.goods_count(StoreId);
        //检查是否已经注册
        if(!EventBus.getDefault().isRegistered(this)){//是否注册eventbus的判断
            EventBus.getDefault().register(this);
        }

    }
    //MainActivity传递的数据
    @Subscribe
    public void onMQttBean(String  type) {

        if ("1".equals(type)){
            viewModel.getxcx_order_count();
        }
    };
    Bundle bundle;
    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://待接单
                         bundle=new Bundle();
                        bundle.putInt("type",1);
                        startActivity(OrderReceivingActivity.class,bundle);
                        break;
                        case 2://待出餐
                         bundle=new Bundle();
                        bundle.putInt("type",2);
                        startActivity(OrderReceivingActivity.class,bundle);
                        break;
                        case 3://退款
                         bundle=new Bundle();
                        bundle.putInt("type",3);
                        startActivity(OrderReceivingActivity.class,bundle);
                        break;

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }

}
