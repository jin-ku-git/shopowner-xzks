package com.youwu.shopowner.ui.order_record;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.RefundDetailsBean;
import com.youwu.shopowner.ui.main.MainActivity;
import com.youwu.shopowner.ui.order_record.bean.OrderGoodsBean;
import com.youwu.shopowner.ui.order_record.bean.RefundBean;

import java.util.ArrayList;

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
 * 2022/09/19
 */

public class OrderGoodsListViewModel extends BaseViewModel<DemoRepository> {


    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    //开始时间的绑定
    public ObservableField<String> start_time = new ObservableField<>("");

    public ObservableField<Integer> null_type=new ObservableField<>();

    //订货列表
    public SingleLiveEvent<ArrayList<OrderGoodsBean>> OrderGoodsBean = new SingleLiveEvent<>();

    public OrderGoodsListViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
            Bundle bundle=new Bundle();
            bundle.putInt("type",4);
            startActivity(MainActivity.class,bundle);
        }
    });

    //开始时间的点击事件
    public BindingCommand StateOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });
    //查询的点击事件
    public BindingCommand obtainOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });

    /**
     * 获取订货列表
     * @param store_id
     */
    public void order_list(String store_id) {
        model.ORDER_LIST(store_id,start_time.get())
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
                            ArrayList<OrderGoodsBean> orderGoodsBean = AppApplication.getObjectList(JsonData, OrderGoodsBean.class);

                            OrderGoodsBean.setValue(orderGoodsBean);

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
     * 获取退货列表
     * @param store_id
     * @param page
     */
    public void refund_list(String store_id, int page) {
        model.REFUND_LIST(store_id,page,"30",start_time.get())
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
                            RefundBean refundBean = JSON.parseObject(toPrettyFormat(JsonData), RefundBean.class);
                            ArrayList<OrderGoodsBean> list=new ArrayList<>();
                            for (int i=0;i<refundBean.getRows().size();i++){
                                OrderGoodsBean orderGoodsBean=new OrderGoodsBean();
                                orderGoodsBean.setId(refundBean.getRows().get(i).getId());
                                orderGoodsBean.setOrder_sn(refundBean.getRows().get(i).getOrder_sn());
                                orderGoodsBean.setCreated_at(refundBean.getRows().get(i).getCreated_at());
                                orderGoodsBean.setStatus(refundBean.getRows().get(i).getStatus());
                                orderGoodsBean.setTotal_quantity(refundBean.getRows().get(i).getTotal_quantity());
                                orderGoodsBean.setStatus_name(refundBean.getRows().get(i).getStatus_name());
                                list.add(orderGoodsBean);
                            }

                            OrderGoodsBean.setValue(list);

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

