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
     * 获取退货商品列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> CARGO_REFUND_GOODS_LIST(String store_id,String id) {
        return apiService.CARGO_REFUND_GOODS_LIST(store_id,id);
    }

    /**
     * 获取报损商品列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> LOSS_GOODS_LIST(String store_id,String id) {
        return apiService.LOSS_GOODS_LIST(store_id,id);
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

    /**
     * 申请退货
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> CARGO_REFUND(String storeId,String remarks,String saasList) {
        return apiService.CARGO_REFUND(storeId,remarks,saasList);
    }

    /**
     * 在售商品数量
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> GOODS_COUNT(String store_id) {
        return apiService.GOODS_COUNT(store_id);
    }

    /**
     * 获取订货列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> ORDER_LIST(String store_id) {
        return apiService.ORDER_LIST(store_id);
    }

    /**
     * 订单列表
     * @param start                 开始时间
     * @param end                   结束时间
     * @param page                  页数
     * @param delivery_method       配送方式
     * @param tel                   手机号
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> ORDER_List(String start,String end,int page,String delivery_method,String tel,String store_id,String order_taking_status,String order_sn) {
        return apiService.ORDER_List(start,end,page,"30",delivery_method,tel,store_id,order_taking_status,order_sn);
    }

    /**
     * 获取销售概况
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> SALES_SITUATION(String type,String store_id) {
        return apiService.SALES_SITUATION(type,store_id);
    }

    /**
     * 核销
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> CLOSE_ORDER(String order_sn) {
        return apiService.CLOSE_ORDER(order_sn);
    }

    /**
     * 订单详情
     * @param order_sn  订单编号
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> ORDER_DETAILS(String order_sn) {
        return apiService.ORDER_DETAILS(order_sn);
    }

    /**
     * 退款订单详情
     * @param order_sn  订单编号
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> REFUND_DETAILS(String order_sn) {
        return apiService.REFUND_DETAILS(order_sn);
    }

    /**
     * 报损原因
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> REASON() {
        return apiService.REASON();
    }

    /**
     * 报损
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> ADD_LOSS_REPORT(String store_id,String mark,String goods_list) {
        return apiService.ADD_LOSS_REPORT(store_id,mark,goods_list);
    }

    /**
     * 盘点
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> SORTING_INVENTORY(String goods_list,String type,String remarks) {
        return apiService.SORTING_INVENTORY(goods_list,type,remarks);
    }

    /**
     * 报损列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> LOSS_REPORT_LIST(String page,String limit,String store_id) {
        return apiService.LOSS_REPORT_LIST(page,limit,store_id);
    }

    /**
     * 盘点列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> INVENTORY_REPORT_LIST(String page,String limit,String store_id) {
        return apiService.INVENTORY_REPORT_LIST(page,limit,store_id);
    }

    /**
     * 沽清列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> SELL_OFF_REPORT_LIST(String page,String limit,String store_id) {
        return apiService.SELL_OFF_REPORT_LIST(page,limit,store_id);
    }

    /**
     * 门店设置
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> UPDATE_STORE_TERMINAL(String is_order, String start, String end, String delivery_method, String is_link, String ukey, String sn, String user) {
        return apiService.UPDATE_STORE_TERMINAL(is_order,start,end,delivery_method,is_link,ukey,sn,user);
    }

    /**
     * 打印
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> PRINT(String order_sn, String store_id) {
        return apiService.PRINT(order_sn,store_id);
    }

    /**
     * 沽清
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> OUT_STOCK(String goods_list,String remarks) {
        return apiService.OUT_STOCK(goods_list,remarks);
    }
}
