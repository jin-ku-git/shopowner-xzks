package com.youwu.shopowner.data.source.http.service;

import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.entity.DemoEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseBean;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by goldze on 2017/6/15.
 */

public interface DemoApiService {
    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<DemoEntity>> demoGet();

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResponse<DemoEntity>> demoPost(@Field("catalog") String catalog);

    /**
     * 检查更新
     * @return
     */
    @GET("app_version")
    Observable<BaseBean<UpDateBean>> GET_APP_VERSION();

    /**
     * 登录
     *
     * @param tel      账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("auth/login")
    Observable<BaseBean<Object>> LOGIN(@Field("tel") String tel, @Field("password") String password);

    /**
     * 获取个人信息
     *
     * @return
     */

    @POST("auth/me")
    Observable<BaseBean<Object>> GET_ME();

    /**
     * 待处理小程序订单数量
     *
     * @return
     */

    @POST("order/xcx_order_count")
    Observable<BaseBean<Object>> XCX_ORDER_COUNT();

    /**
     * 小程序订单
     *
     * @param type 1.待接单2.待出餐3.带退款
     * @return
     */
    @FormUrlEncoded
    @POST("order/xcx_order_list")
    Observable<BaseBean<Object>> XCX_ORDER_LIST(@Field("type") String type);

    /**
     * 接单、拒单、出餐
     *
     * @param order_sn 订单编号
     * @param status   2接单3.拒单4.出餐'
     * @return
     */
    @FormUrlEncoded
    @POST("order/edit_order_status")
    Observable<BaseBean<Object>> DEIT_ORDER_STATUS(@Field("order_sn") String order_sn, @Field("status") String status);

    /**
     * 小程序订单审核
     * @param type
     * @param order_sn
     * @param refund_reason
     * @param modify_stock
     * @return
     */
    @FormUrlEncoded
    @POST("order/audit_order_refund")
    Observable<BaseBean<Object>> AUDIT_ORDER_REFUND(@Field("type") int type,@Field("order_sn") String order_sn,@Field("refund_reason") String refund_reason,@Field("modify_stock") String modify_stock );

    /**
     * 获取订货分类
     *
     * @return
     */

    @POST("goods/goods_category")
    Observable<BaseBean<Object>> GOODS_CATEGORY();

    /**
     * 获取订货商品列表
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("cargo/goods_list")
    Observable<BaseBean<Object>> GOODS_LIST(@Field("store_id") String store_id,@Field("category_id") String id);

    /**
     * 更新门店自动接单
     *
     * @param is_order       是都自动接单 1自动接单 2 手动接单
     * @param start         开始时间
     * @param end           结束时间
     * @return
     */
    @FormUrlEncoded
    @POST("update_store")
    Observable<BaseBean<Object>> UPDATE_STORE(@Field("is_order") String is_order, @Field("start") String start, @Field("end") String end);

    /**
     * 申请订货
     *
     * @param store_id 订单编号
     * @return
     */
    @FormUrlEncoded
    @POST("cargo/add_order")
    Observable<BaseBean<Object>> ADD_ORDER(@Field("store_id") String store_id,@Field("arrival_time") String arrival_time,@Field("goods_list") String goods_list);
}
