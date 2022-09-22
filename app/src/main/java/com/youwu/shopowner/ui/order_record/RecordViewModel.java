package com.youwu.shopowner.ui.order_record;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.order_record.bean.OrderGoodsBean;
import com.youwu.shopowner.ui.order_record.bean.RecordBean;

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
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.youwu.shopowner.app.AppApplication.toPrettyFormat;

/**
 * 2022/09/22
 */

public class RecordViewModel extends BaseViewModel<DemoRepository> {


    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    //开始时间的绑定
    public ObservableField<String> start_time = new ObservableField<>("");

    public ObservableField<Integer> null_type=new ObservableField<>();

    //订货列表
    public SingleLiveEvent<ArrayList<RecordBean.RowsBean>> recordBeanEvent = new SingleLiveEvent<>();

    public RecordViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);

    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });




    /**
     * 获取记录列表
     * @param store_id
     */
    public void Record_list(int page,String limit,String store_id) {
        model.LOSS_REPORT_LIST(page+"",limit,store_id)
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

                            KLog.d("报损记录："+JsonData);
                            RecordBean orderGoodsBean = JSON.parseObject(toPrettyFormat(JsonData), RecordBean.class);
                            recordBeanEvent.setValue(orderGoodsBean.getRows());

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
     * 获取盘点记录列表
     * @param store_id
     */
    public void Inventory_Record_list(int page,String limit,String store_id) {
        model.INVENTORY_REPORT_LIST(page+"",limit,store_id)
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

                            KLog.d("盘点记录："+JsonData);
                            RecordBean orderGoodsBean = JSON.parseObject(toPrettyFormat(JsonData), RecordBean.class);
                            recordBeanEvent.setValue(orderGoodsBean.getRows());

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
     * 获取沽清记录列表
     * @param store_id
     */
    public void Sell_Off_Record_list(int page,String limit,String store_id) {
        model.SELL_OFF_REPORT_LIST(page+"",limit,store_id)
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

                            KLog.d("沽清记录："+JsonData);
                            RecordBean orderGoodsBean = JSON.parseObject(toPrettyFormat(JsonData), RecordBean.class);
                            recordBeanEvent.setValue(orderGoodsBean.getRows());

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
