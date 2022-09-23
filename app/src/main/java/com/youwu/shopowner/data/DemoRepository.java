package com.youwu.shopowner.data;

import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.data.source.HttpDataSource;
import com.youwu.shopowner.data.source.LocalDataSource;
import com.youwu.shopowner.entity.DemoEntity;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repositor）
 * Created by goldze on 2019/3/26.
 */
public class DemoRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static DemoRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private DemoRepository(@NonNull HttpDataSource httpDataSource,
                           @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static DemoRepository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (DemoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DemoRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<Object> login() {
        return mHttpDataSource.login();
    }

    @Override
    public Observable<DemoEntity> loadMore() {
        return mHttpDataSource.loadMore();
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoGet() {
        return mHttpDataSource.demoGet();
    }

    @Override
    public Observable<BaseResponse<DemoEntity>> demoPost(String catalog) {
        return mHttpDataSource.demoPost(catalog);
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    /**
     * 检查更新
     * @return
     */
    public Observable<BaseBean<UpDateBean>> GET_APP_VERSION() {
        return mHttpDataSource.GET_APP_VERSION();
    }
    /**
     *登录
     * @return
     * @param name 手机号
     * @param password 密码
     */
    public Observable<BaseBean<Object>> LOGIN(String name, String password) {
        return mHttpDataSource.LOGIN(name,password);
    }

    /**
     *获取个人信息
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> GET_ME() {
        return mHttpDataSource.GET_ME();
    }

    /**
     *待处理小程序订单数量
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> XCX_ORDER_COUNT() {
        return mHttpDataSource.XCX_ORDER_COUNT();
    }

    /**
     *小程序订单列表
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> XCX_ORDER_LIST(String type) {
        return mHttpDataSource.XCX_ORDER_LIST(type);
    }

    /**
     * 接单、拒单、出餐
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> DEIT_ORDER_STATUS(String order_sn, String status) {
        return mHttpDataSource.DEIT_ORDER_STATUS(order_sn,status);
    }

    /**
     * 小程序订单审核
     * @param status
     * @param order_sn
     * @param refund_reason
     * @param modify_stock
     * @return
     */
    public Observable<BaseBean<Object>> AUDIT_ORDER_REFUND(int status, String order_sn, String refund_reason, String modify_stock ) {
        return mHttpDataSource.AUDIT_ORDER_REFUND(status,order_sn,refund_reason,modify_stock);
    }

    /**
     *获取盘点分类
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> GOODS_CATEGORY() {
        return mHttpDataSource.GOODS_CATEGORY();
    }

    /**
     * 获取订货商品列表
     * @param store_id  店铺id
     * @param id         分类id
     * @return
     */
    public Observable<BaseBean<Object>> GOODS_LIST(String store_id,String id) {
        return mHttpDataSource.GOODS_LIST(store_id,id);
    }

    /**
     * 获取退货商品列表
     * @param store_id  店铺id
     * @param category_id         分类id
     * @return
     */
    public Observable<BaseBean<Object>> CARGO_REFUND_GOODS_LIST(String store_id,String category_id) {
        return mHttpDataSource.CARGO_REFUND_GOODS_LIST(store_id,category_id);
    }

    /**
     * 获取报损商品列表
     * @param store_id  店铺id
     * @param id         分类id
     * @return
     */
    public Observable<BaseBean<Object>> LOSS_GOODS_LIST(String store_id,String id) {
        return mHttpDataSource.LOSS_GOODS_LIST(store_id,id);
    }

    /**
     *  更新门店自动接单
     * @return
     */
    public Observable<BaseBean<Object>> UPDATE_STORE(String is_order, String start, String end) {
        return mHttpDataSource.UPDATE_STORE(is_order,start,end);
    }

    /**
     * 申请订货
     * @param storeId  订单编号
     * @param saasList  订货内容
     * @return
     */
    public Observable<BaseBean<Object>> ADD_ORDER(String storeId,String arrival_time,String saasList) {
        return mHttpDataSource.ADD_ORDER(storeId,arrival_time,saasList);
    }
    /**
     * 申请退货
     * @param storeId  订单编号
     * @param saasList  订货内容
     * @return
     */
    public Observable<BaseBean<Object>> CARGO_REFUND(String storeId,String remarks,String saasList) {
        return mHttpDataSource.CARGO_REFUND(storeId,remarks,saasList);
    }

    /**
     *获取在售商品数量
     * @return
     */
    @Override
    public Observable<BaseBean<Object>> GOODS_COUNT(String store_id) {
        return mHttpDataSource.GOODS_COUNT(store_id);
    }

    /**
     * 获取订货列表
     * @param store_id  订单编号
     * @return
     */
    public Observable<BaseBean<Object>> ORDER_LIST(String store_id) {
        return mHttpDataSource.ORDER_LIST(store_id);
    }

    /**
     * 订单列表
     * @param start  开始时间戳
     * @param end     结束时间戳
     * @return
     */
    public Observable<BaseBean<Object>> ORDER_List(String start,String end,int page,String delivery_method,String tel,String store_id,String order_taking_status,String order_sn) {
        return mHttpDataSource.ORDER_List(start,end,page,delivery_method,tel,store_id,order_taking_status,order_sn);
    }

    /**
     * 获取销售概况
     * @param type   1.今日 2.本周 3.本月 4.本季度
     * @return
     */
    public Observable<BaseBean<Object>> SALES_SITUATION(String type,String store_id) {
        return mHttpDataSource.SALES_SITUATION(type,store_id);
    }

    /**
     * 核销
     * @param order_sn  订单编号
     * @return
     */
    public Observable<BaseBean<Object>> CLOSE_ORDER(String order_sn) {
        return mHttpDataSource.CLOSE_ORDER(order_sn);
    }

    /**
     * 订单详情
     * @param order_sn  订单编号
     * @return
     */
    public Observable<BaseBean<Object>> ORDER_DETAILS(String order_sn) {
        return mHttpDataSource.ORDER_DETAILS(order_sn);
    }

    /**
     * 退款订单详情
     * @param order_sn  订单编号
     * @return
     */
    public Observable<BaseBean<Object>> REFUND_DETAILS(String order_sn) {
        return mHttpDataSource.REFUND_DETAILS(order_sn);
    }

    /**
     * 报损原因
     * @return
     */
    public Observable<BaseBean<Object>> REASON() {
        return mHttpDataSource.REASON();
    }

    /**
     * 报损
     * @return
     */
    public Observable<BaseBean<Object>> ADD_LOSS_REPORT(String store_id,String mark,String goods_list) {
        return mHttpDataSource.ADD_LOSS_REPORT(store_id,mark,goods_list);
    }

    /**
     * 盘点
     * @return
     */
    public Observable<BaseBean<Object>> SORTING_INVENTORY(String goods_list,String type,String remarks) {
        return mHttpDataSource.SORTING_INVENTORY(goods_list,type,remarks);
    }

    /**
     * 报损列表
     * @return
     */
    public Observable<BaseBean<Object>> LOSS_REPORT_LIST(String page,String limit,String store_id) {
        return mHttpDataSource.LOSS_REPORT_LIST(page,limit,store_id);
    }
    /**
     * 盘点列表
     * @return
     */
    public Observable<BaseBean<Object>> INVENTORY_REPORT_LIST(String page,String limit,String store_id) {
        return mHttpDataSource.INVENTORY_REPORT_LIST(page,limit,store_id);
    }

    /**
     * 沽清列表
     * @return
     */
    public Observable<BaseBean<Object>> SELL_OFF_REPORT_LIST(String page,String limit,String store_id) {
        return mHttpDataSource.SELL_OFF_REPORT_LIST(page,limit,store_id);
    }

    /**
     *  门店设置
     *
     * @return
     */
    public Observable<BaseBean<Object>> UPDATE_STORE_TERMINAL(String is_order, String start, String end, String delivery_method, String is_link, String ukey, String sn, String user) {
        return mHttpDataSource.UPDATE_STORE_TERMINAL(is_order,start,end,delivery_method,is_link,ukey,sn,user);
    }

    /**
     * 打印
     * @param order_sn
     * @param store_id
     * @return
     */
    public Observable<BaseBean<Object>> PRINT(String order_sn, String store_id) {
        return mHttpDataSource.PRINT(order_sn,store_id);
    }
    /**
     * 沽清
     * @param goods_list
     * @return
     */
    public Observable<BaseBean<Object>> OUT_STOCK(String goods_list,String remarks) {
        return mHttpDataSource.OUT_STOCK(goods_list,remarks);
    }
}
