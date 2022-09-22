package com.youwu.shopowner.ui.order_goods;

import android.app.Application;
import android.app.Dialog;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.UserBean;
import com.youwu.shopowner.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner.ui.fragment.bean.XXCOrderCountBean;

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
 * 2022/09/12
 */

public class OrderReceivingViewModel extends BaseViewModel<DemoRepository> {

    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();



    public OrderReceivingViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }

    public ObservableField<String> store_name = new ObservableField<>("");


    public ObservableField<Integer> order_wait_count = new ObservableField<>();
    public ObservableField<Integer> order_mak_count = new ObservableField<>();
    public ObservableField<Integer> order_refund_count = new ObservableField<>();


    //小程序待处理数量的绑定
    public SingleLiveEvent<XXCOrderCountBean> xxc_order_count = new SingleLiveEvent<>();

    //小程序订单列表
    public SingleLiveEvent<ArrayList<XXCOrderBean>> xxc_order_list = new SingleLiveEvent<>();

    public ObservableField<Integer> null_type=new ObservableField<>();

    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });


    /**
     * 获取个人信息
     */
    public void getMe() {
        model.GET_ME()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<BaseBean<Object>>() {
                    @Override
                    public void onNext(BaseBean<Object> response) {
                        if (response.isOk()){
                            String submitJsonData = new Gson().toJson(response.data);

                            UserBean userBean= JSON.parseObject(toPrettyFormat(submitJsonData), UserBean.class);

                            AppApplication.spUtils.put("StoreId", userBean.getStore_id()+"");
                            AppApplication.spUtils.put("StoreName", userBean.getStore_name());
                            AppApplication.spUtils.put("Id", userBean.getId()+"");
                            AppApplication.spUtils.put("Name", userBean.getName());
                            AppApplication.spUtils.put("topic", userBean.getTopic());
                            AppApplication.spUtils.put("tel", userBean.getTel());

                            AppApplication.spUtils.put("is_order", userBean.getIs_order());
                            AppApplication.spUtils.put("start_time", userBean.getStart());
                            AppApplication.spUtils.put("end_time", userBean.getEnd());

                            store_name.set(userBean.getStore_name());


                        }else {
                            RxToast.normal(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
//                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                    @Override
                    public void onComplete() {
                        //关闭对话框
//                        dismissDialog();
                    }
                });
    }


    /**
     * 待处理小程序订单数量
     */
    public void getxcx_order_count() {
        model.XCX_ORDER_COUNT()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<BaseBean<Object>>() {
                    @Override
                    public void onNext(BaseBean<Object> response) {

                        if (response.isOk()){
                            String submitJsonData = new Gson().toJson(response.data);

                            XXCOrderCountBean xxcOrderCountBean= JSON.parseObject(toPrettyFormat(submitJsonData), XXCOrderCountBean.class);
                            Log.e("返回结果-----已解析：",xxcOrderCountBean.getOrder_wait_count()+"");

                            order_wait_count.set(xxcOrderCountBean.getOrder_wait_count());
                            order_mak_count.set(xxcOrderCountBean.getOrder_mak_count());
                            order_refund_count.set(xxcOrderCountBean.getOrder_refund_count());

                        }else {
                            RxToast.normal(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        //关闭对话框
//                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                    @Override
                    public void onComplete() {
                        //关闭对话框
//                        dismissDialog();
                    }
                });
    }

    /**
     * 小程序订单列表
     * @param type  //状态 1 待接单 2 待出餐 3 待退款
     */
    public void xcx_order_list(String type) {
        model.XCX_ORDER_LIST(type)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

//                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<BaseBean<Object>>() {
                    @Override
                    public void onNext(BaseBean<Object> response) {

                        if (response.isOk()){
                            String JsonData = new Gson().toJson(response.data);
                            ArrayList<XXCOrderBean> vipRechargeBean = AppApplication.getObjectList(JsonData, XXCOrderBean.class);

                            xxc_order_list.setValue(vipRechargeBean);

                            if (xxc_order_list.getValue().size()==0){
                                null_type.set(0);

                            }else {
                                null_type.set(1);
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
                            RxToast.normal(((ResponseThrowable) throwable).message);
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
     * 接单、拒单、出餐
     * @param order_sn
     * @param status
     */
    public void edit_order_status(String order_sn, String status) {
        model.DEIT_ORDER_STATUS(order_sn,status)
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
                        //关闭对话框
                        dismissDialog();
                        if (response.isOk()){
                            String JsonData = new Gson().toJson(response.data);
                            IntegerEvent.setValue(1);

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
     * 小程序订单审核
     * @param status
     * @param order_sn
     * @param refund_reason
     * @param modify_stock
     * @param dialog
     */
    public void audit_order_refund(final int status, String order_sn, String refund_reason, String modify_stock, final Dialog dialog) {
        model.AUDIT_ORDER_REFUND(status,order_sn,refund_reason,modify_stock)
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
                            dialog.dismiss();
                            if (status==1){
                                IntegerEvent.setValue(20);
                            }else {
                                IntegerEvent.setValue(21);
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
