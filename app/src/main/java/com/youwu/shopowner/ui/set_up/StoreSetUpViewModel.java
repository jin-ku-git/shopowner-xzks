package com.youwu.shopowner.ui.set_up;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.order_record.bean.RefundBean;

import java.util.Arrays;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.youwu.shopowner.app.AppApplication.toPrettyFormat;

/**
 * 2022/08/12
 */

public class StoreSetUpViewModel extends BaseViewModel<DemoRepository> {

    //开始时间的绑定
    public ObservableField<String> start_time = new ObservableField<>("");
    //开始时间的绑定
    public ObservableField<String> end_time = new ObservableField<>("");

    //key的绑定
    public ObservableField<String> KeyName = new ObservableField<>("");
    //Value的绑定
    public ObservableField<String> ValueName = new ObservableField<>("");

    public SingleLiveEvent<Boolean> state = new SingleLiveEvent<>();
    public ObservableField<SettingBean> singleLiveField= new ObservableField<>();

    public ObservableField<Boolean> oneBool= new ObservableField<>(false);
    public ObservableField<Boolean> twoBool= new ObservableField<>(false);
    public ObservableField<Boolean> threeBool= new ObservableField<>(false);
    public ObservableField<Boolean> fourBool= new ObservableField<>(false);

    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public StoreSetUpViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);


    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });

    //开始时间的点击事件
    public BindingCommand StateOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //结束时间的点击事件
    public BindingCommand EndOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });
    //确认的点击事件
    public BindingCommand ConfirmOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(3);
        }
    });
    //重置的点击事件
    public BindingCommand ResetOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(5);
        }
    });
    //打印测试的点击事件
    public BindingCommand PrintTestOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            PrintTest();
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 更新门店自动接单
     * @param type
     */
    public void update_store(int type,String delivery_method,String feie_type) {
        model.UPDATE_STORE_TERMINAL(type+"",start_time.get(),end_time.get(),delivery_method,feie_type,KeyName.get(),ValueName.get())
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

                            IntegerEvent.setValue(4);
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

    /**
     * 打印测试
     *
     */
    public void PrintTest() {
        model.PRINT_TEST()
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

                            RxToast.normal("打印中，请稍后");
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

    /**
     * 获取门店设置列表
     */
    public void setting_list() {
        model.SETTING_LIST()
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

                            SettingBean settingBean= JSON.parseObject(toPrettyFormat(JsonData), SettingBean.class);

                                    singleLiveField.set(settingBean);

                            start_time.set(singleLiveField.get().getStart());
                            end_time.set(singleLiveField.get().getEnd());
                                    if (singleLiveField.get().getIs_link()==1){
                                        KeyName.set(singleLiveField.get().getFeie_print().getUkey());
                                        ValueName.set(singleLiveField.get().getFeie_print().getSn());
                                    }
                            String[] sourceStrArray = singleLiveField.get().getDelivery_method().split(",");
                            for (int i = 0; i < sourceStrArray.length; i++) {
                                System.out.println(sourceStrArray[i]);
                            }
                            boolean isThere = Arrays.asList(sourceStrArray).contains("1");
                            boolean isThere2 = Arrays.asList(sourceStrArray).contains("2");
                            boolean isThere3 = Arrays.asList(sourceStrArray).contains("3");
                            boolean isThere4 = Arrays.asList(sourceStrArray).contains("4");
                            if (isThere){
                                KLog.d("包含1");
                                oneBool.set(true);
                            }
                            if (isThere2){
                                KLog.d("包含2");
                                twoBool.set(true);
                            }
                            if (isThere3){
                                KLog.d("包含3");
                                threeBool.set(true);
                            }
                            if (isThere4){
                                KLog.d("包含4");
                                fourBool.set(true);
                            }


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
}

