package com.youwu.shopowner.ui.report_form;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.report_form.bean.SalesOverviewBean;


import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.youwu.shopowner.app.AppApplication.toPrettyFormat;


/**
 * 2022/06/01
 */

public class SalesOverviewViewModel extends BaseViewModel<DemoRepository> {


    public ObservableField<SalesOverviewBean> entity = new ObservableField<>();
    //状态的绑定
    public ObservableField<Integer> state = new ObservableField<>();

    //饼状图的绑定
    public SingleLiveEvent<SalesOverviewBean> entity_List = new SingleLiveEvent<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();



    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    public SalesOverviewViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });


    //今日的点击事件
    public BindingCommand TodayOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            state.set(1);
            sales_situation("1");
        }
    });
    //本周的点击事件
    public BindingCommand ThisWeekOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            state.set(2);
            sales_situation("2");
        }
    });
    //本月的点击事件
    public BindingCommand ThisMonthOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            state.set(3);
            sales_situation("3");
        }
    });
    //本月的点击事件
    public BindingCommand ThisQuarterOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            state.set(4);
            sales_situation("4");
        }
    });

    /**
     * 获取销售概况
     * @param type  1.今日 2.本周 3.本月 4.本季度
     */
    public void sales_situation(String type) {
        String store_id= AppApplication.spUtils.getString("StoreId");
        model.SALES_SITUATION(type,store_id)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<BaseBean<Object>>() {
                    @Override
                    public void onNext(BaseBean<Object> response) {
                        if (response.isOk()){
                            String JsonData = new Gson().toJson(response.data);

                            SalesOverviewBean salesOverviewBean = JSON.parseObject(toPrettyFormat(JsonData), SalesOverviewBean.class);
                            entity.set(salesOverviewBean);
                            entity_List.setValue(salesOverviewBean);
                        }else {
                            RxToast.normal(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                    @Override
                    public void onComplete() {
                        //关闭对话框
                        dismissDialog();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
