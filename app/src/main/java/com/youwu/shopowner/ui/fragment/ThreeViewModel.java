package com.youwu.shopowner.ui.fragment;

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
import com.youwu.shopowner.ui.fragment.bean.GroupBean;
import com.youwu.shopowner.ui.fragment.bean.RowsOrdersBean;
import com.youwu.shopowner.ui.fragment.bean.SAASOrderBean;
import com.youwu.shopowner.ui.fragment.bean.SaleBillBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
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

public class ThreeViewModel extends BaseViewModel<DemoRepository> {

//    public ObservableField<UserBean.RoleBean> entity = new ObservableField<>();
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();

    public ObservableField<Integer> null_type=new ObservableField<>();



    //订单列表
    public SingleLiveEvent<ArrayList<SaleBillBean>> OrderList = new SingleLiveEvent<>();


    public ThreeViewModel(@NonNull Application application, DemoRepository repository) {
        super(application,repository);
    }


    //筛选的点击事件
    public BindingCommand screenOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });


    /**
     * 订单列表
     * @param start
     * @param end
     */
    public void order_list(String start, String end,  String delivery_method, String tel, String store_id,int order_taking_status,final int page,String order_sn) {
        model.ORDER_List(start,end,page,delivery_method,tel,store_id,order_taking_status+"",order_sn)
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

                            RowsOrdersBean rowsOrdersBean = JSON.parseObject(toPrettyFormat(JsonData), RowsOrdersBean.class);
                            ArrayList<SaleBillBean> list=  rowsOrdersBean.getRows();

                            OrderList.setValue(list);
                            if (page==1&&list.size()==0){
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
