package com.youwu.shopowner.ui.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;


import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.ui.set_up.SettingsActivity;
import com.youwu.shopowner.ui.set_up.StoreSetUpActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 2022/09/12
 */

public class FourViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<String> nameEvent = new ObservableField<>();//名称
    public ObservableField<String> telEvent = new ObservableField<>();//账号


    public FourViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    //设置
    public BindingCommand SetonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SettingsActivity.class);
        }
    });

    //门店设置
    public BindingCommand StoreSetonClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(StoreSetUpActivity.class);
        }
    });

}
