package com.youwu.shopowner.ui.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.FragmentOneBinding;
import com.youwu.shopowner.NotificationClickReceiver;
import com.youwu.shopowner.ui.order_goods.OrderReceivingActivity;

import me.goldze.mvvmhabit.base.BaseFragment;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 2022/09/12
 */

public class OneFragment extends BaseFragment<FragmentOneBinding,OneViewModel> {


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


    }
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



}
