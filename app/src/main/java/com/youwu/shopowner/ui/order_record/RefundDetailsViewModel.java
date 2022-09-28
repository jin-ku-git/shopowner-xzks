package com.youwu.shopowner.ui.order_record;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.OrderDetailsBean;
import com.youwu.shopowner.ui.order_record.bean.CargoRefundBean;

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
 * 2021/9/23
 */

public class RefundDetailsViewModel extends BaseViewModel<DemoRepository> {

    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();


    public SingleLiveEvent<CargoRefundBean> CargoRefundEvent = new SingleLiveEvent<>();

    public RefundDetailsViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    //退货日期
    public ObservableField<String> ReceiptTime =new ObservableField<>();

    //订单状态
    public ObservableField<Integer> status =new ObservableField<>();
    //总价c
    public ObservableField<String> TotalPrice =new ObservableField<>();
    //种类
    public ObservableField<String> TotalType =new ObservableField<>();
    //数量
    public ObservableField<String> TotalQuantity =new ObservableField<>();
    //订单状态
    public ObservableField<String> OrderState =new ObservableField<>();



    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


    /**
     * 退货详情
     * @param order_sn
     */
    public void refund_details(String order_sn) {
        model.CARGO_REFUND_DETAILS(order_sn)
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
                            KLog.d("退货详情："+JsonData);

                            CargoRefundBean cargoRefundBean= JSON.parseObject(toPrettyFormat(JsonData), CargoRefundBean.class);

                            CargoRefundEvent.setValue(cargoRefundBean);

                            ReceiptTime.set(cargoRefundBean.getCreated_at());
                            TotalPrice.set(cargoRefundBean.getTotal_price());
                            TotalType.set(cargoRefundBean.getDetails().size()+"");
                            TotalQuantity.set(cargoRefundBean.getTotal_quantity()+"");
                            status.set(cargoRefundBean.getStatus());
                            OrderState.set(cargoRefundBean.getStatus_name());

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
