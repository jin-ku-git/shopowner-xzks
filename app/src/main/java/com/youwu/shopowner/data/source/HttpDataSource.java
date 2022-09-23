package com.youwu.shopowner.data.source;

import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.entity.DemoEntity;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * Created by goldze on 2019/3/26.
 */
public interface HttpDataSource {
    //模拟登录
    Observable<Object> login();

    //模拟上拉加载
    Observable<DemoEntity> loadMore();

    Observable<BaseResponse<DemoEntity>> demoGet();

    Observable<BaseResponse<DemoEntity>> demoPost(String catalog);


    //检查更新
    Observable<BaseBean<UpDateBean>> GET_APP_VERSION();

    //登录
    Observable<BaseBean<Object>> LOGIN(String name, String password);

    //获取个人信息
    Observable<BaseBean<Object>> GET_ME();

    //待处理小程序订单数量
    Observable<BaseBean<Object>> XCX_ORDER_COUNT();

    //小程序订单列表
    Observable<BaseBean<Object>> XCX_ORDER_LIST(String type);

    //接单、拒单、出餐
    Observable<BaseBean<Object>> DEIT_ORDER_STATUS(String order_sn, String status);

    //小程序订单审核
    Observable<BaseBean<Object>> AUDIT_ORDER_REFUND(int status, String order_sn, String refund_reason, String modify_stock);

    //获取订货分类
    Observable<BaseBean<Object>> GOODS_CATEGORY();

    //获取订货商品列表
    Observable<BaseBean<Object>> GOODS_LIST(String store_id,String id);

    //获取退货商品列表
    Observable<BaseBean<Object>> CARGO_REFUND_GOODS_LIST(String store_id,String id);

    //获取报损商品列表
    Observable<BaseBean<Object>> LOSS_GOODS_LIST(String store_id,String id);

    //更新门店自动接单
    Observable<BaseBean<Object>> UPDATE_STORE(String is_order, String start, String end);

    //申请订货
    Observable<BaseBean<Object>> ADD_ORDER(String storeId,String arrival_time,String saasList);

    //申请退货
    Observable<BaseBean<Object>> CARGO_REFUND(String storeId,String remarks,String saasList);

    //在售商品数量
    Observable<BaseBean<Object>> GOODS_COUNT(String store_id);

    //获取订货列表
    Observable<BaseBean<Object>> ORDER_LIST(String store_id);

    //订单列表
    Observable<BaseBean<Object>> ORDER_List(String start, String end, int page, String delivery_method, String tel,String store_id,String order_taking_status,String order_sn);

    //获取销售概况
    Observable<BaseBean<Object>> SALES_SITUATION(String type,String store_id);

    //核销
    Observable<BaseBean<Object>> CLOSE_ORDER(String order_sn);

    //订单详情
    Observable<BaseBean<Object>> ORDER_DETAILS(String order_sn);

    //退款订单详情
    Observable<BaseBean<Object>> REFUND_DETAILS(String order_sn);

    //报损原因
    Observable<BaseBean<Object>> REASON();
    //报损
    Observable<BaseBean<Object>> ADD_LOSS_REPORT(String store_id,String mark,String goods_list);
    //盘点
    Observable<BaseBean<Object>> SORTING_INVENTORY(String goods_list,String type,String remarks);
    //报损列表
    Observable<BaseBean<Object>> LOSS_REPORT_LIST(String page,String limit,String store_id);
    //盘点列表
    Observable<BaseBean<Object>> INVENTORY_REPORT_LIST(String page,String limit,String store_id);
    //沽清列表
    Observable<BaseBean<Object>> SELL_OFF_REPORT_LIST(String page,String limit,String store_id);
    //门店设置
    Observable<BaseBean<Object>> UPDATE_STORE_TERMINAL(String is_order, String start, String end, String delivery_method, String is_link, String ukey, String sn, String user);

    //打印
    Observable<BaseBean<Object>> PRINT(String order_sn, String store_id);
    //沽清
    Observable<BaseBean<Object>> OUT_STOCK(String goods_list,String remarks);
}
