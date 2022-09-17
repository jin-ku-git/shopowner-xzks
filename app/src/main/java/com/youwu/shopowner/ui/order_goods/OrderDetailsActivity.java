package com.youwu.shopowner.ui.order_goods;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityOrderDetailsBinding;


import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @author: Administrator
 * @date: 2022/9/17
 */
public class OrderDetailsActivity extends BaseActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel> {
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
                        break;

                }
            }
        });
    }
}
