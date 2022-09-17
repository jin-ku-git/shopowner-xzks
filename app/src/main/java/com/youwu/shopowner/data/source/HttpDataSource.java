package com.youwu.shopowner.data.source;

import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.entity.DemoEntity;

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

    //更新门店自动接单
    Observable<BaseBean<Object>> UPDATE_STORE(String is_order, String start, String end);

    //申请订货
    Observable<BaseBean<Object>> ADD_ORDER(String storeId,String arrival_time,String saasList);
}
