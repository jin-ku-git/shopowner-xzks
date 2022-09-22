package com.youwu.shopowner.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.ui.fragment.FourViewModel;
import com.youwu.shopowner.ui.fragment.OneViewModel;
import com.youwu.shopowner.ui.fragment.ThreeViewModel;
import com.youwu.shopowner.ui.fragment.TwoViewModel;
import com.youwu.shopowner.ui.goods_operate.InventoryDetailsViewModel;
import com.youwu.shopowner.ui.goods_operate.InventoryViewModel;
import com.youwu.shopowner.ui.goods_operate.LossReportingDetailsViewModel;
import com.youwu.shopowner.ui.goods_operate.LossReportingViewModel;
import com.youwu.shopowner.ui.goods_operate.ReturnGoodsDetailsViewModel;
import com.youwu.shopowner.ui.goods_operate.ReturnGoodsViewModel;
import com.youwu.shopowner.ui.goods_operate.SellOffDetailsViewModel;
import com.youwu.shopowner.ui.goods_operate.SellOffViewModel;
import com.youwu.shopowner.ui.login.LoginViewModel;
import com.youwu.shopowner.ui.main.MainViewModel;
import com.youwu.shopowner.ui.network.NetWorkViewModel;
import com.youwu.shopowner.ui.order_record.OrderGoodsListViewModel;
import com.youwu.shopowner.ui.order_goods.ConfirmOrderViewModel;
import com.youwu.shopowner.ui.order_goods.OrderDetailsViewModel;
import com.youwu.shopowner.ui.order_goods.OrderReceivingViewModel;
import com.youwu.shopowner.ui.order_goods.RefundOrderDetailsViewModel;
import com.youwu.shopowner.ui.order_record.RecordViewModel;
import com.youwu.shopowner.ui.report_form.SalesOverviewViewModel;
import com.youwu.shopowner.ui.set_up.ModifyPasswordViewModel;
import com.youwu.shopowner.ui.set_up.SettingsViewModel;
import com.youwu.shopowner.ui.set_up.StoreSetUpViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final DemoRepository mRepository;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, DemoRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NetWorkViewModel.class)) {
            return (T) new NetWorkViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OneViewModel.class)) {
            return (T) new OneViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(FourViewModel.class)) {
            return (T) new FourViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TwoViewModel.class)) {
            return (T) new TwoViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ThreeViewModel.class)) {
            return (T) new ThreeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(StoreSetUpViewModel.class)) {
            return (T) new StoreSetUpViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            return (T) new SettingsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ConfirmOrderViewModel.class)) {
            return (T) new ConfirmOrderViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderReceivingViewModel.class)) {
            return (T) new OrderReceivingViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderDetailsViewModel.class)) {
            return (T) new OrderDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RefundOrderDetailsViewModel.class)) {
            return (T) new RefundOrderDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderGoodsListViewModel.class)) {
            return (T) new OrderGoodsListViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SalesOverviewViewModel.class)) {
            return (T) new SalesOverviewViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ModifyPasswordViewModel.class)) {
            return (T) new ModifyPasswordViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(LossReportingViewModel.class)) {
            return (T) new LossReportingViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(LossReportingDetailsViewModel.class)) {
            return (T) new LossReportingDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(InventoryViewModel.class)) {
            return (T) new InventoryViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(InventoryDetailsViewModel.class)) {
            return (T) new InventoryDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SellOffViewModel.class)) {
            return (T) new SellOffViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SellOffDetailsViewModel.class)) {
            return (T) new SellOffDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ReturnGoodsViewModel.class)) {
            return (T) new ReturnGoodsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ReturnGoodsDetailsViewModel.class)) {
            return (T) new ReturnGoodsDetailsViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RecordViewModel.class)) {
            return (T) new RecordViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
