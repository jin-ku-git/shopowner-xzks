package com.youwu.shopowner.ui.set_up;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.app.UserUtils;
import com.youwu.shopowner.databinding.ActivityModifyPasswordBinding;
import com.youwu.shopowner.databinding.ActivitySettingsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.login.LoginActivity;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * @author: Administrator
 * @date: 2022/9/20
 */
public class ModifyPasswordActivity extends BaseActivity<ActivityModifyPasswordBinding, ModifyPasswordViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_modify_password;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ModifyPasswordViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ModifyPasswordViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        break;
                    case 2:
                        RxToast.normal("取消");
                        break;
                    case 3:
                        SharedPreferences sharedPreferences = getSharedPreferences("TestXML", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.clear().commit();


                        UserUtils.logout();


                        Intent intent=new Intent();
                        intent.setClass(ModifyPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
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
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色
    }
}
