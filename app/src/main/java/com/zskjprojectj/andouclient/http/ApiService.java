package com.zskjprojectj.andouclient.http;


import com.zskjprojectj.andouclient.entity.TestBean;
import com.zskjprojectj.andouclient.entity.WXPayBean;
import com.zskjprojectj.andouclient.entity.mall.MallBuyBean;
import com.zskjprojectj.andouclient.entity.mall.MallBuyNowBean;
import com.zskjprojectj.andouclient.entity.mall.MallCommentBean;
import com.zskjprojectj.andouclient.entity.mall.MallDetailsBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsDetailsDataBean;
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.entity.mall.MallSettlementBean;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;
import com.zskjprojectj.andouclient.model.Address;
import com.zskjprojectj.andouclient.model.BalanceDetail;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.model.Merchant;
import com.zskjprojectj.andouclient.model.MerchantsResponse;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.model.OrderDetail;
import com.zskjprojectj.andouclient.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * 查询
     */
    @FormUrlEncoded
    @POST("cook/query")
    Observable<BaseResult<TestBean.ResultBean>> getinfo(@Field("key") String key, @Field("menu") String menu);

    /**
     * 获取商城首页数据
     */
    @POST("api/goods/index")
    Observable<BaseResult<MallHomeDataBean>> getMallInfo();

    /**
     * 商城商品主页展示
     */
    @POST("api/goods/goods")
    @FormUrlEncoded
    Observable<BaseResult<MallGoodsDetailsDataBean>> mallDetailsShow(@Field("id") String id, @Field("uid") String uid);


    /**
     * 获取商城商品分类
     */
    @POST("api/goods/goods_cate")
    Observable<BaseResult<List<MallGoodsCateBean>>> getMallGoodsCate();


    /**
     * 商城商品收藏/取消收藏
     */
    @POST("api/goods/collection")
    @FormUrlEncoded
    Observable<BaseResult<Object>> mallGoodsCollection(@Field("id") String id, @Field("uid") String uid, @Field("token") String token, @Field("type") String type);

    /**
     * 商城商品详情
     */
    @POST("api/goods/details")
    @FormUrlEncoded
    Observable<BaseResult<MallDetailsBean>> mallDetails(@Field("id") String id);

    /**
     * 商城商品评论
     */
    @POST("api/goods/comment")
    @FormUrlEncoded
    Observable<BaseResult<List<MallCommentBean>>> mallComment(@Field("id") String id);

    /**
     * 商城商品规格
     */
    @POST("api/goods/specslist")
    @FormUrlEncoded
    Observable<BaseResult<MallBuyBean>> buySpecification(@Field("id") String id);

    /**
     * 商城商户详情
     */
    @POST("api/merchant/merchant_goods")
    @FormUrlEncoded
    Observable<BaseResult<MallShoppingHomeBean>> mallMerchant(@Field("id") String id, @Field("uid") String uid,
                                                              @Field("keyword") String keyword,
                                                              @Field("type_id") String type_id,
                                                              @Field("price_sort") String price_sort,
                                                              @Field("volume_sort") String volume_sort);

    /**
     * 商城立即购买
     */
    @POST("api/order/add_order")
    @FormUrlEncoded
    Observable<BaseResult<MallBuyNowBean>> MallBuyNow(@Field("uid") String uid,
                                                      @Field("token") String token,
                                                      @Field("goods_id") String goods_id,
                                                      @Field("merchant_id") String merchant_id,
                                                      @Field("goods_sku_id") String goods_sku_id,
                                                      @Field("num") String num);

    /**
     * 商城购买结算页
     */
    @POST("api/order/settlement")
    @FormUrlEncoded
    Observable<BaseResult<MallSettlementBean>> MallSettlement(@Field("uid") String uid,
                                                              @Field("token") String token,
                                                              @Field("order_sn") String order_sn);

    /**
     * 获取支付方式
     */
    @POST("api/common/pay_ways")
    Observable<BaseResult<List<MallPayWaysBean>>> getMallPayWays();


    /**
     * 微信支付
     */
    @FormUrlEncoded
    @POST("api/order/pay")
    Observable<BaseResult<WXPayBean>> MallWXPayWays(@Field("uid") String uid,
                                                    @Field("token") String token,
                                                    @Field("sNo") String sNo,
                                                    @Field("pay_id") String pay_id,
                                                    @Field("is_integral") String is_integral
    );


    /**
     * 新增收货地址
     */
    @POST("api/Usersaddress/address_add")
    @FormUrlEncoded
    Observable<BaseResult<Object>> addAddress(@Field("uid") String uid,
                                              @Field("token") String token,
                                              @Field("name") String name,
                                              @Field("mobile") String mobile,
                                              @Field("province_id") String province_id,
                                              @Field("city_id") String city_id,
                                              @Field("area_id") String area_id,
                                              @Field("address") String address,
                                              @Field("is_defualt") String is_defualt);

    /**
     * 修改收货地址
     */
    @POST("api/Usersaddress/address_edit")
    @FormUrlEncoded
    Observable<BaseResult<Object>> editAddress(@Field("id") String id,
                                               @Field("uid") String uid,
                                               @Field("token") String token,
                                               @Field("name") String name,
                                               @Field("mobile") String mobile,
                                               @Field("province_id") String province_id,
                                               @Field("city_id") String city_id,
                                               @Field("area_id") String area_id,
                                               @Field("address") String address,
                                               @Field("is_defualt") String is_defualt);

    /**
     * 设置默认收货地址
     */
    @POST("api/Usersaddress/defualt")
    @FormUrlEncoded
    Observable<BaseResult<Object>> defualtAddress(@Field("id") String id,
                                                  @Field("uid") String uid,
                                                  @Field("token") String token);

    /**
     * 添加购物车
     */
    @POST("api/cart/addcar")
    @FormUrlEncoded
    Observable<BaseResult<Object>> addCar(@Field("uid") String uid,
                                          @Field("token") String token,
                                          @Field("goods_id") String goods_id,
                                          @Field("merchant_id") String merchant_id,
                                          @Field("goods_sku_id") String goods_sku_id);

    /**
     * 购物车列表
     */
    @POST("api/cart/index")
    @FormUrlEncoded
    Observable<BaseResult<List<CartItem>>> cart(@Field("uid") String uid,
                                                @Field("token") String token);

    /**
     * 删除购物车
     */
    @POST("api/cart/delcar")
    @FormUrlEncoded
    Observable<BaseResult<Object>> delCart(@Field("uid") String uid,
                                           @Field("token") String token,
                                           @Field("id") String id);

    /**
     * 修改购物车
     */
    @POST("api/cart/update_num")
    @FormUrlEncoded
    Observable<BaseResult<Object>> editCart(@Field("uid") String uid,
                                            @Field("token") String token,
                                            @Field("id") String id,
                                            @Field("type") String type);

    /**
     * 登录
     */
    @POST("api/login/login_p")
    @FormUrlEncoded
    Observable<BaseResult<User>> login(@Field("phone") String uid,
                                       @Field("password") String token);

    /**
     * 发送验证码
     */
    @POST("api/login/send")
    @FormUrlEncoded
    Observable<BaseResult<Object>> sendCode(@Field("phone") String uid,
                                            @Field("type") String type);

    /**
     * 验证码注册
     */
    @POST("api/login/reg_p")
    @FormUrlEncoded
    Observable<BaseResult<Object>> register(@Field("phone") String uid,
                                            @Field("password") String type,
                                            @Field("verify") String verify);

    /**
     * 忘记密码
     */
    @POST("api/login/forget")
    @FormUrlEncoded
    Observable<BaseResult<Object>> forgetPassword(@Field("phone") String uid,
                                                  @Field("new_password") String type,
                                                  @Field("verify") String verify);

    /**
     * 收货地址列表
     */
    @POST("api/Usersaddress/address")
    @FormUrlEncoded
    Observable<BaseResult<List<Address>>> address(@Field("uid") String uid,
                                                  @Field("token") String token);

    /**
     * 删除收货地址
     */
    @POST("api/Usersaddress/address_del")
    @FormUrlEncoded
    Observable<BaseResult<Object>> delAddress(@Field("uid") String uid,
                                              @Field("token") String token,
                                              @Field("id") String id);

    /**
     * 商户列表
     */
    @POST("api/merchant/merchants")
    Observable<BaseResult<MerchantsResponse>> merchants();

    /**
     * 商户详情
     */
    @POST("api/merchant/merchant_goods")
    @FormUrlEncoded
    Observable<BaseResult<Merchant>> merchantDetail(@Field("id") String id);

    /**
     * 余额明细
     */
    @POST("api/wallet/index")
    @FormUrlEncoded
    Observable<BaseResult<BalanceDetail>> balanceDetail(@Field("uid") String uid,
                                                        @Field("token") String token);

    /**
     * 余额明细
     */
    @POST("api/wallet/cash")
    @FormUrlEncoded
    Observable<BaseResult<BalanceDetail>> cashDetail(@Field("uid") String uid,
                                                     @Field("token") String token);

    /**
     * 余额提现
     */
    @POST("api/wallet/cash_withdrawal")
    @FormUrlEncoded
    Observable<BaseResult<Object>> cash(@Field("uid") String uid,
                                        @Field("token") String token,
                                        @Field("money") float money,
                                        @Field("phone") String phone,
                                        @Field("num") String num);

    /**
     * 订单列表
     */
    @POST("api/order/index")
    @FormUrlEncoded
    Observable<BaseResult<List<Order>>> orderList(@Field("uid") String uid,
                                                  @Field("token") String token,
                                                  @Field("type") String type);

    /**
     * 订单详情
     */
    @POST("api/order/details")
    @FormUrlEncoded
    Observable<BaseResult<OrderDetail>> orderDetail(@Field("uid") String uid,
                                                    @Field("token") String token,
                                                    @Field("order_sn") String order_sn);

//    /**
//     * 注册
//     *
//     * @param code    设备码
//     * @param invCode 邀请码【非必传】
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("/member")
//    Observable<BaseResult<UserBean>> register(@Field("code") String code, @Field("invCode") String invCode);
//
//    /**
//     * 广告
//     */
//    @FormUrlEncoded
//    @POST("/gtad")
//    Observable<BaseResult<List<AdvertisingBean>>> adver(@Field("type") String type, @Field("page") int page);
//
//    /**
//     * 统计人数在线
//     */
//    @POST("/online")
//    Observable<BaseResult<Object>> onlinenum();
//
//    /**
//     * 版本更新
//     */
//    @POST("/version")
//    Observable<BaseResult<AppVersionBean>> getversion();
//
//
//    /**
//     * 热门数据
//     *
//     * @param page
//     * @param type
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("/vlists")
//    Observable<BaseResult<List<VideoBean>>> getHotData(@Field("page") int page, @Field("type") String type);
//
//    /**
//     * 广告数据
//     *
//     * @return
//     */
//    @POST("/indexport")
//    Observable<BaseResult<HomeAdsBean>> getAdsData();
//
//    /**
//     * 我的消息
//     */
//    @FormUrlEncoded
//    @POST("/getNews")
//    Observable<BaseResult<List<MessageBean>>> getnewsData(@Field("type") int type, @Field("page") int page);
//
//    /**
//     * 联系客服，我的消息
//     */
//    @POST("/getminfo")
//    Observable<BaseResult<TelphoneBean>> gettelData();
//
//    /**
//     * 分享二维码接口
//     */
//    @POST("/recomment")
//    Observable<BaseResult<ShareBean>> getshareData();
//
//
//    /**
//     * 获取关注
//     *
//     * @param type
//     * @param page
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("/lists")
//    Observable<BaseResult<List<VideoBean>>> getCollectList(@Field("type") String type, @Field("page") int page);
//
//    @FormUrlEncoded
//    @POST("/collect")
//    Observable<BaseResult<JSONObject>> collect(@Field("type") int type, @Field("id") String id, @Field("done") int done);
//
//
//    /**
//     * 获取直播列表
//     *
//     * @param page
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("/getVport")
//    Observable<BaseResult<LiveBean>> liveListData(@Field("page") int page);
//
//    /**
//     * 视频详情
//     *
//     * @param id
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("/vinfo")
//    Observable<BaseResult<VideoDetailBean>> getVideoDetail(@Field("id") String id);
//
//    /**
//     * 任务列表
//     */
//    @POST("/task")
//    Observable<BaseResult<ShareTaskBean>> getTaskData();
//
//    /**
//     * 任务记录
//     */
//    @FormUrlEncoded
//    @POST("/invl")
//    Observable<BaseResult<List<ShareRecordBean>>> getRecordData(@Field("page") int page);
//
//    /**
//     * 充值方式
//     */
//    @POST("/getpw")
//    Observable<BaseResult<List<VipPayBean>>> getVipData();
//
//    /**
//     * 直播机构页面
//     *
//     * @param page
//     * @param id
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("/pfminfo")
//    Observable<BaseResult<PlatformBean>> getPlatformData(@Field("page") int page, @Field("pid") String id);


}
