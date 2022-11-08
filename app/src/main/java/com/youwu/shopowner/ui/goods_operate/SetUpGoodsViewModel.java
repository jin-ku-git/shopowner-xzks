package com.youwu.shopowner.ui.goods_operate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.youwu.shopowner.data.DemoRepository;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.CommunityBean;
import com.youwu.shopowner.ui.set_up.SettingBean;

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

/**
 * 2022/11/02
 */

public class SetUpGoodsViewModel extends BaseViewModel<DemoRepository> {


    //套餐还是商品的绑定
    public ObservableField<Integer> type = new ObservableField<>();
    //商品名称的绑定
    public ObservableField<String> GoodsName = new ObservableField<>("");
    //商品售价的绑定
    public ObservableField<String> GoodsPrice = new ObservableField<>("");
    //商品市场价的绑定
    public ObservableField<String> MarketValue = new ObservableField<>("");
    //商品库存的绑定
    public ObservableField<String> GoodsStock = new ObservableField<>("");

    public ObservableField<Integer> statusField= new ObservableField<>();


    //使用LiveData
    public SingleLiveEvent<Integer> IntegerEvent = new SingleLiveEvent<>();
    public SetUpGoodsViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);


    }


    //返回的点击事件
    public BindingCommand returnOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });


    //确认的点击事件
    public BindingCommand ConfirmOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(1);
        }
    });



    //重置的点击事件
    public BindingCommand ResetOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            IntegerEvent.setValue(2);
        }
    });

    /**
     * 更新商品信息
     *
     * @param type      商品类型 1商品 2套餐
     * @param goods_sku  商品sku
     * @param package_id    套餐id
     */
    public void updateStoreGoods(String type,String goods_sku,String package_id) {
        model.UPDATE_STORE_GOODS(type,goods_sku,package_id,GoodsName.get(),GoodsPrice.get(),MarketValue.get(),statusField.get()+"",GoodsStock.get())
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

                            IntegerEvent.setValue(3);

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
