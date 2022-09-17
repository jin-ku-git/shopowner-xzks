package com.youwu.shopowner.data.source.http;

import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.data.source.HttpDataSource;
import com.youwu.shopowner.data.source.http.service.DemoApiService;
import com.youwu.shopowner.entity.DemoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * Created by goldze on 2019/3/26.
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private DemoApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(DemoApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(DemoApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Object> login() {
        return Observable.just(new Object()).delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<DemoEntity> loadMore() {
        return Observable.create(new ObservableOnSubscribe<DemoEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DemoEntity> observableEmitter) throws Exception {
                DemoEntity entity = new DemoEntity();
                List<DemoEntity.ItemsEntity> itemsEntities = new ArrayList<>();
                //模拟一部分假数据
                for (int i = 0; i < 10; i++) {
                    DemoEntity.ItemsEntity item = new DemoEntity.ItemsEntity();
                    item.setId(-1);
                    item.setName("模拟条目");
                    itemsEntities.add(item);
                }
                entity.setItems(itemsEntities);
                observableEmitter.onNext(entity);
            }
        }).delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoGet() {
        return apiService.demoGet();
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoPost(String catalog) {
        return apiService.demoPost(catalog);
    }

    /**
     * 检查更新
     * @return
     */
    @Override
    public Observable<BaseBean<UpDateBean>> GET_APP_VERSION() {
        return apiService.GET_APP_VERSION();
    }

    /**
     * 登录
     * @param tel
     * @param password
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> LOGIN(String tel, String password) {
        return apiService.LOGIN(tel,password);
    }

    /**
     * 获取个人信息
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> GET_ME() {
        return apiService.GET_ME();
    }

    /**
     * 待处理小程序订单数量
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> XCX_ORDER_COUNT() {
        return apiService.XCX_ORDER_COUNT();
    }

    /**
     * 小程序订单列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> XCX_ORDER_LIST(String type) {
        return apiService.XCX_ORDER_LIST(type);
    }

    /**
     * 小程序订单列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> DEIT_ORDER_STATUS(String order_sn, String status) {
        return apiService.DEIT_ORDER_STATUS(order_sn,status);
    }

    /**
     * 小程序订单审核
     * @param status
     * @param order_sn
     * @param refund_reason
     * @param modify_stock
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> AUDIT_ORDER_REFUND(int status, String order_sn, String refund_reason, String modify_stock) {
        return apiService.AUDIT_ORDER_REFUND(status,order_sn,refund_reason,modify_stock);
    }

    /**
     * 获取订货分类
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> GOODS_CATEGORY() {
        return apiService.GOODS_CATEGORY();
    }

    /**
     * 获取订货商品列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> GOODS_LIST(String store_id,String id) {
        return apiService.GOODS_LIST(store_id,id);
    }

    /**
     * 更新门店自动接单
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> UPDATE_STORE(String is_order, String start, String end) {
        return apiService.UPDATE_STORE(is_order,start,end);
    }

    /**
     * 申请订货
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> ADD_ORDER(String storeId,String arrival_time,String saasList) {
        return apiService.ADD_ORDER(storeId,arrival_time,saasList);
    }
}
