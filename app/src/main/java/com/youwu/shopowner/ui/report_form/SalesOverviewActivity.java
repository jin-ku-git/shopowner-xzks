package com.youwu.shopowner.ui.report_form;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivitySalesOverviewBinding;
import com.youwu.shopowner.ui.report_form.bean.SalesOverviewBean;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import java.util.List;
import java.util.Random;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 报表页面
 * 2022/06/01
 */
public class SalesOverviewActivity extends BaseActivity<ActivitySalesOverviewBinding, SalesOverviewViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sales_overview;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SalesOverviewViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SalesOverviewViewModel.class);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

        viewModel.state.set(1);

        viewModel.sales_situation(viewModel.state.get()+"");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
