package com.zskjprojectj.andouclient.http;

import com.google.gson.JsonElement;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.model.ListData;
import com.zskjprojectj.andouclient.entity.AboutusBean;
import com.zskjprojectj.andouclient.entity.BrowsingBean;
import com.zskjprojectj.andouclient.entity.CheckLogisticsBean;
import com.zskjprojectj.andouclient.entity.EnvelopesBean;
import com.zskjprojectj.andouclient.entity.IndexHomeBean;
import com.zskjprojectj.andouclient.entity.InformationBean;
import com.zskjprojectj.andouclient.entity.InvitationBean;
import com.zskjprojectj.andouclient.entity.MerchantHomeTypeBean;
import com.zskjprojectj.andouclient.entity.MyFocusonBean;
import com.zskjprojectj.andouclient.entity.MycollectionBean;
import com.zskjprojectj.andouclient.entity.MymessageBean;
import com.zskjprojectj.andouclient.entity.NewuserBean;
import com.zskjprojectj.andouclient.entity.RefundReasonBean;
import com.zskjprojectj.andouclient.entity.PersonalBean;
import com.zskjprojectj.andouclient.entity.SetBean;
import com.zskjprojectj.andouclient.entity.TestBean;
import com.zskjprojectj.andouclient.entity.ViproteBean;
import com.zskjprojectj.andouclient.entity.WXPayBean;
import com.zskjprojectj.andouclient.entity.WalletrecharBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelCategoryBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailCommentBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailsBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeDetailsBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelSettlementBean;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelBean;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelDetailsBean;
import com.zskjprojectj.andouclient.entity.mall.MallBuyBean;
import com.zskjprojectj.andouclient.entity.mall.MallBuyNowBean;
import com.zskjprojectj.andouclient.entity.mall.MallCarBean;
import com.zskjprojectj.andouclient.entity.mall.MallCommentBean;
import com.zskjprojectj.andouclient.entity.mall.MallDetailsBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsDetailsDataBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsListBean;
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.entity.mall.MallSettlementBean;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;
import com.zskjprojectj.andouclient.model.ADProvince;
import com.zskjprojectj.andouclient.model.Address;
import com.zskjprojectj.andouclient.model.BalanceDetail;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.FoodCategory;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.model.RestaurantCategory;
import com.zskjprojectj.andouclient.model.IntegralDetail;
import com.zskjprojectj.andouclient.model.Merchant;
import com.zskjprojectj.andouclient.model.MerchantsResponse;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.model.OrderDetail;
import com.zskjprojectj.andouclient.model.RestaurantOrder;
import com.zskjprojectj.andouclient.model.Review;
import com.zskjprojectj.andouclient.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
     * 获取商城商品分类
     */
    @POST("api/goods/cate")
    @FormUrlEncoded
    Observable<BaseResult<List<MallGoodsCateBean>>> getMallGoodsCate(@Field("id") String id);


    /**
     * 商城产品列表
     */
    @POST("api/goods/good_list")
    @FormUrlEncoded
    Observable<BaseResult<ListData<MallGoodsListBean>>> mallGoodsList(@Field("keyword") String keyword,
                                                                      @Field("cate_id") String uid,
                                                                      @Field("is_recommend") String is_recommend,
                                                                      @Field("is_bargain") String is_bargain,
                                                                      @Field("price_sort") String price_sort,
                                                                      @Field("volume_sort") String volume_sort,
                                                                      @Field("start_sort") String start_sort,
                                                                      @Field("page") int page
    );


    /**
     * 商城商品收藏/取消收藏
     */
    @POST("api/goods/collection")
    @FormUrlEncoded
    Observable<BaseResult<Object>> mallGoodsCollection(@Field("id") String id, @Field("uid") String uid, @Field("token") String token, @Field("type") String type);

    /**
     * 店铺收藏/取消收藏
     *
     * @param id
     * @param uid
     * @param token
     * @param type
     * @return
     */
    @POST("api/goods/follow")
    @FormUrlEncoded
    Observable<BaseResult<Object>> mallgoodsfollow(@Field("id") String id, @Field("uid") String uid, @Field("token") String token, @Field("type") String type);

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
     * 购物车购买
     */
    @POST("api/order/add_order_car")
    @FormUrlEncoded
    Observable<BaseResult<MallCarBean>> OrderBuyCar(@Field("uid") String uid,
                                                    @Field("token") String token,
                                                    @Field("id") String id);


    /**
     * 商城商户详情
     */
    @POST("api/merchant/merchant_goods")
    @FormUrlEncoded
    Observable<BaseResult<MallShoppingHomeBean>> mallMerchant(@Field("id") String id, @Field("uid") String uid,
                                                              @Field("keyword") String keyword,
                                                              @Field("type_id") String type_id,
                                                              @Field("price_sort") String price_sort,
                                                              @Field("volume_sort") String volume_sort,
                                                              @Field("page") int page
    );

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
     * 快递查询
     */
    @POST("api/order/express")
    @FormUrlEncoded
    Observable<BaseResult<CheckLogisticsBean>> CheckLogistics(@Field("uid") String uid,
                                                              @Field("token") String token,
                                                              @Field("express_id") String express_id,
                                                              @Field("courier_num") String courier_num);


    /**
     * 获取支付方式
     */
    @POST("api/common/pay_ways")
    Observable<BaseResult<List<MallPayWaysBean>>> getMallPayWays();

    /**
     * 获取充值方式
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/wallet/payWays")
    Observable<BaseResult<List<MallPayWaysBean>>> getWalletPayWays(@Field("uid") String uid, @Field("token") String token);

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
     * 微信充值
     *
     * @param uid
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("api/wallet/recharge")
    Observable<BaseResult<WXPayBean>> MallWXPayWaysrecharge(@Field("uid") String uid,
                                                            @Field("token") String token,
                                                            @Field("money") String money,
                                                            @Field("mobile") String mobile,
                                                            @Field("method") String method
    );

    /**
     * 会员购买
     *
     * @param uid
     * @param token
     * @param pay_id
     * @return
     */
    @FormUrlEncoded
    @POST("api/users/vip_recharge")
    Observable<BaseResult<WXPayBean>> vip_recharge(@Field("uid") String uid,
                                                   @Field("token") String token,
                                                   @Field("pay_id") String pay_id
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
                                               @Field("tel") String mobile,
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
    Observable<BaseResult<ListData<CartItem>>> cart(@Field("uid") String uid,
                                                    @Field("token") String token,
                                                    @Field("page") int page
    );

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
     * 微信登录
     *
     * @param code
     * @return
     */
    @POST("api/login/wxlogin")
    @FormUrlEncoded
    Observable<BaseResult<User>> loginweixin(@Field("code") String code);

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

    @POST("api/users/upmodel")
    @FormUrlEncoded
    Observable<BaseResult<Object>> upmodel(@Field("uid") String uid,
                                           @Field("token") String token,
                                           @Field("phone") String phone,
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
     * 商户列表merchants two
     */
    @POST("api/merchant/merchants")
    @FormUrlEncoded
    Observable<BaseResult<MerchantsResponse>> merchants_two(@Field("page") int page);

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
    Observable<BaseResult<ListData<Order>>> orderList(@Field("uid") String uid,
                                                      @Field("token") String token,
                                                      @Field("type") String type,
                                                      @Field("page") int page
    );

    /**
     * 通知消息
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/index/notification_center")
    @FormUrlEncoded
    Observable<BaseResult<List<MymessageBean>>> notificationcenter(@Field("uid") String uid,
                                                                   @Field("token") String token);

    /**
     * 通知消息
     *
     * @param id
     * @param uid
     * @return
     */
    @POST("api/index/information")
    @FormUrlEncoded
    Observable<BaseResult<InformationBean>> information(@Field("id") String id,
                                                        @Field("uid") String uid);

    /**
     * 订单详情
     */
    @POST("api/order/details")
    @FormUrlEncoded
    Observable<BaseResult<OrderDetail>> getOrderDetail(@Field("uid") String uid,
                                                       @Field("token") String token,
                                                       @Field("order_sn") String order_sn,
                                                       @Field("did") String did);


    /**
     * 确认收货
     */
    @POST("api/order/confirm")
    @FormUrlEncoded
    Observable<BaseResult<Object>> confirm(@Field("uid") String uid,
                                           @Field("token") String token,
                                           @Field("id") String id);


    /**
     * 申请退款
     */
    @POST("api/refund/apply")
    @FormUrlEncoded
    Observable<BaseResult<Object>> mallrefund(@Field("uid") String uid,
                                              @Field("token") String token,
                                              @Field("order_goods_id") String order_goods_id,
                                              @Field("reason_id") String reason_id,
                                              @Field("content") String content,
                                              @Field("image") String image);


    /**
     * 修改昵称
     *
     * @param uid
     * @param token
     * @param name
     * @return
     */
    @POST("api/modification/user_head")
    @FormUrlEncoded
    Observable<BaseResult<Object>> user_head(@Field("id") String uid,
                                             @Field("token") String token,
                                             @Field("name") String name);

    /**
     * 添加商品评论
     */
    @POST("api/order/addcomment")
    @FormUrlEncoded
    Observable<BaseResult<Object>> addcomment(@Field("uid") String uid,
                                              @Field("token") String token,
                                              @Field("goods_id") String goods_id,
                                              @Field("order_id") String order_id,
                                              @Field("merchants_id") String merchants_id,
                                              @Field("content") String content,
                                              @Field("stars") String stars);

    /**
     * 首页
     */
    @POST("api/index/index")
    Observable<BaseResult<IndexHomeBean>> index();


    /**
     * 退款
     */
    @POST("api/refund/apply")
    @FormUrlEncoded
    Observable<BaseResult<Object>> refundapply(@Field("uid") String uid,
                                               @Field("token") String token,
                                               @Field("order_goods_id") String order_goods_id,
                                               @Field("reason_id") String reason_id,
                                               @Field("content") String content);


    /**
     * 退货
     */
    @POST("api/refund/return_goods")
    @FormUrlEncoded
    Observable<BaseResult<Object>> refundgoods(@Field("uid") String uid,
                                               @Field("token") String token,
                                               @Field("order_goods_id") String order_goods_id);

    /**
     * 退款原因
     */
    @POST("api/refund/reason")
    Observable<BaseResult<List<RefundReasonBean>>> refundreason();


    /**
     * 酒店分类
     */
    @POST("api/hotel/cate")
    Observable<BaseResult<List<HotelCategoryBean>>> hotelCategory();


    /**
     * 酒店商家列表
     */
    @POST("api/hotel/hotellist")
    @FormUrlEncoded
    Observable<BaseResult<ListData<HotelHomeBean>>> hotelHomeList(@Field("keywords") String keywords,
                                                                  @Field("star_price") String star_price,
                                                                  @Field("end_price") String end_price,
                                                                  @Field("stars_all") String stars_all,
                                                                  @Field("type") String type,
                                                                  @Field("page") int page);


    /**
     * 酒店搜索配置
     */
    @POST("api/hotel/condition")
    Observable<BaseResult<HotelSearchConditionBean>> hotelSearchCondition();


    /**
     * list - 酒店商家详情
     */
    @POST("api/details/list")
    @FormUrlEncoded
    Observable<BaseResult<HotelDetailsBean>> hotelDetails(@Field("uid") String uid, @Field("id") String id);


    /**
     * list - 酒店商家房间列表
     */
    @POST("api/details/room_list")
    @FormUrlEncoded
    Observable<BaseResult<HotelDetailReserveBean>> hotelDetailsHomeList(@Field("merchant_id") int merchant_id);


    /**
     * list - 酒店商家房间详情
     */
    @POST("api/details/hotelSel")
    @FormUrlEncoded
    Observable<BaseResult<HotelHomeDetailsBean>> hotelHomeDetails(@Field("id") String id);


    /**
     * list - 酒店商家评论列表
     */
    @POST("api/details/commnets")
    @FormUrlEncoded
    Observable<BaseResult<List<HotelDetailCommentBean>>> hotelDetailsCommentList(@Field("id") String id, @Field("page") String page);


    /**
     * list - 酒店结算页
     */
    @POST("api/htorder/settlement")
    @FormUrlEncoded
    Observable<BaseResult<HotelSettlementBean>> hotelSettlement(@Field("uid") String uid,
                                                                @Field("token") String token,
                                                                @Field("start") String start,
                                                                @Field("end") String end,
                                                                @Field("id") String id);

    /**
     * list - 酒店预订
     */
    @POST("api/htorder/add_order")
    @FormUrlEncoded
    Observable<BaseResult<WXPayBean>> hotelOrder(@Field("uid") String uid,
                                                 @Field("token") String token,
                                                 @Field("id") String id,
                                                 @Field("merchant_id") String merchant_id,
                                                 @Field("start_time") String start_time,
                                                 @Field("end_time") String end_time,
                                                 @Field("real_name") String real_name,
                                                 @Field("mobile") String mobile,
                                                 @Field("num") String num,
                                                 @Field("day_num") String day_num,
                                                 @Field("pay_way") String pay_way,
                                                 @Field("is_integral") String is_integral);

    /**
     * list - 酒店订单
     */
    @POST("api/hotel/order")
    @FormUrlEncoded
    Observable<BaseResult<ListData<MeHotelBean>>> mehotelOrder(@Field("uid") String uid,
                                                               @Field("token") String token,
                                                               @Field("type") String type,
                                                               @Field("page") int page);

    /**
     * list - 酒店预订详情
     */
    @POST("api/htorder/orderdatails")
    @FormUrlEncoded
    Observable<BaseResult<MeHotelDetailsBean>> mehotelOrderDetails(@Field("uid") String uid,
                                                                   @Field("token") String token,
                                                                   @Field("book_sn") String book_sn);

    /**
     * 添加酒店评论
     */
    @POST("api/details/addcomment")
    @FormUrlEncoded
    Observable<BaseResult<Object>> addhotelcomment(@Field("uid") String uid,
                                                   @Field("token") String token,
                                                   @Field("goods_id") String goods_id,
                                                   @Field("order_id") String order_id,
                                                   @Field("merchants_id") String merchants_id,
                                                   @Field("content") String content,
                                                   @Field("stars") String stars,
                                                   @Field("dianzhan") String dianzhan);

    /**
     * 添加酒店点赞
     */
    @POST("api/users/fabulous")
    @FormUrlEncoded
    Observable<BaseResult<Object>> addhotelfabulous(@Field("uid") String uid,
                                                    @Field("token") String token,
                                                    @Field("id") String id);

    /**
     * 绑定手机号
     */
    @POST("api/login/bindmobile")
    @FormUrlEncoded
    Observable<BaseResult<User>> bindlogin(@Field("phone") String uid,
                                           @Field("password") String token,
                                           @Field("verify") String verify,
                                           @Field("name") String name,
                                           @Field("openid") String openid,
                                           @Field("avator") String avator);

    /**
     * 个人中心
     */
    @POST("api/wallet/personal")
    @FormUrlEncoded
    Observable<BaseResult<PersonalBean>> getpersonal(@Field("uid") String uid,
                                                     @Field("token") String token);

    /**
     * 设置
     *
     * @param uid
     * @param token
     * @return
     */

    @POST("api/opinion/set")
    @FormUrlEncoded
    Observable<BaseResult<SetBean>> set(@Field("uid") String uid,
                                        @Field("token") String token);

    /**
     * 浏览痕迹
     */
    @POST("api/users/merchant_record")
    @FormUrlEncoded
    Observable<BaseResult<List<BrowsingBean>>> merchantrecord(@Field("uid") String uid,
                                                              @Field("token") String token);

    /**
     * 我的平台币
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/wallet/integral")
    @FormUrlEncoded
    Observable<BaseResult<IntegralDetail>> integralDetail(@Field("uid") String uid,
                                                          @Field("token") String token);

    /**
     * 我的收藏
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/collection")
    @FormUrlEncoded
    Observable<BaseResult<List<MycollectionBean>>> usercollection(@Field("uid") String uid,
                                                                  @Field("token") String token);


    /**
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/follow")
    @FormUrlEncoded
    Observable<BaseResult<List<MyFocusonBean>>> usersfollow(@Field("uid") String uid,
                                                            @Field("token") String token);

    /**
     * 获取充值详细信息
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/wallet/rechar")
    @FormUrlEncoded
    Observable<BaseResult<WalletrecharBean>> walletrechar(@Field("uid") String uid,
                                                          @Field("token") String token);

    /**
     * 商家入驻
     *
     * @param uid
     * @param type_id
     * @param name
     * @param user_name
     * @param tel
     * @param province_id
     * @param city_id
     * @param area_id
     * @param address
     * @param desc
     * @param bannerImg
     * @param logo_img
     * @param management_img
     * @return
     */
    @POST("api/merchant/information")
    @FormUrlEncoded
    Observable<BaseResult<Object>> uploadMerchantsInfo(@Field("uid") String uid,
                                                       @Field("token") String token,
                                                       @Field("type_id") int type_id,
                                                       @Field("name") String name,
                                                       @Field("user_name") String user_name,
                                                       @Field("tel") String tel,
                                                       @Field("province_id") int province_id,
                                                       @Field("city_id") int city_id,
                                                       @Field("area_id") int area_id,
                                                       @Field("address") String address,
                                                       @Field("desc") String desc,
                                                       @Field("banner_img") String bannerImg,
                                                       @Field("logo_img") String logo_img,
                                                       @Field("management_img") String management_img);

    /**
     * 地址
     *
     * @return
     */
    @POST("api/common/district")
    Observable<BaseResult<List<ADProvince>>> districts();

    /**
     * 图片上传
     *
     * @param uid
     * @param file
     * @return
     */
    @POST("api/goods/uploads")
    @Multipart
    Observable<BaseResult<String>> uploadImg(@Part("uid") RequestBody uid, @Part("token") RequestBody token, @Part MultipartBody.Part file);

    /**
     * 邀请码
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/invitations")
    @FormUrlEncoded
    Observable<BaseResult<InvitationBean>> invitationsvip(@Field("uid") String uid, @Field("token") String token);

    /**
     * 绑定上下级
     *
     * @param uid
     * @param token
     * @param code
     * @return
     */
    @POST("api/users/binding")
    @FormUrlEncoded
    Observable<BaseResult<Object>> binding(@Field("uid") String uid, @Field("token") String token, @Field("code") String code);

    /**
     * 添加酒店点赞
     */
    @POST("api/opinion/index")
    @FormUrlEncoded
    Observable<BaseResult<Object>> opinionindex(@Field("uid") String uid,
                                                @Field("token") String token,
                                                @Field("content") String content);

    /**
     * 酒店搜索配置
     */
    @POST("api/index/about")
    Observable<BaseResult<AboutusBean>> about();

    /**
     * 添加酒店点赞
     */
    @POST("api/goods/quit")
    @FormUrlEncoded
    Observable<BaseResult<Object>> quit(@Field("uid") String uid,
                                        @Field("token") String token);

    /**
     * 会员规则
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/vip_rote")
    @FormUrlEncoded
    Observable<BaseResult<ViproteBean>> vip_rote(@Field("uid") String uid,
                                                 @Field("token") String token);

    /**
     * 红包领取
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/envelopes_add")
    @FormUrlEncoded
    Observable<BaseResult<Object>> envelopes_add(@Field("uid") String uid,
                                                 @Field("token") String token);

    /**
     * 判断是否是新用户
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/new_user")
    @FormUrlEncoded
    Observable<BaseResult<NewuserBean>> new_user(@Field("uid") String uid,
                                                      @Field("token") String token);
    @POST("api/users/envelopes")
    @FormUrlEncoded
    Observable<BaseResult<EnvelopesBean>> envelopes(@Field("uid") String uid,
                                                    @Field("token") String token);

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

    @POST("api/gourmet/delicious")
    Observable<BaseResult<List<RestaurantCategory>>> getFoodCategory();

    @FormUrlEncoded
    @POST("api/gourmet/list")
    Observable<BaseResult<ListData<Restaurant>>> getRestaurants(@Field("name") String name,
                                                                @Field("cate_id") String id,
                                                                @Field("sort") String sort,
                                                                @Field("page") int page);

    @FormUrlEncoded
    @POST("api/gourmet/details")
    Observable<BaseResult<Restaurant>> getRestaurantDetail(@Field("uid") String uid,
                                                           @Field("id") String id);

    @FormUrlEncoded
    @POST("api/gourmet/dishtype")
    Observable<BaseResult<List<FoodCategory>>> getFoodCategory(@Field("merchants_id") String merchants_id);

    @FormUrlEncoded
    @POST("api/gourmet/booking")
    Observable<BaseResult<List<Food>>> getCart(@Field("user_id") String user_id,
                                               @Field("token") String token,
                                               @Field("merchant_id") String merchants_id);

    @FormUrlEncoded
    @POST("api/gourmet/upd_foods")
    Observable<BaseResult<Object>> changeFoodCart(@Field("uid") String user_id,
                                                  @Field("merchant_id") String merchants_id,
                                                  @Field("id") String id,
                                                  @Field("type") int type);

    @FormUrlEncoded
    @POST("api/gourmet/timely")
    Observable<BaseResult<JsonElement>> payBill(@Field("uid") String user_id,
                                                @Field("merchant_id") String merchant_id,
                                                @Field("people") String people,
                                                @Field("remark") String remark,
                                                @Field("dinnertime") String dinnertime,
                                                @Field("is_integral") int is_integral,
                                                @Field("method") int method);

    @FormUrlEncoded
    @POST("api/gourmet/order")
    Observable<BaseResult<RestaurantOrderIListResponse>> getOrderList(@Field("uid") String user_id,
                                                                      @Field("page") int merchant_id,
                                                                      @Field("status") int status);

    @FormUrlEncoded
    @POST("api/gourmet/order_details")
    Observable<BaseResult<RestaurantOrder>> getOrderDetail(@Field("uid") String user_id,
                                                           @Field("id") String id);

    @FormUrlEncoded
    @POST("api/gourmet/refund")
    Observable<BaseResult<Object>> refund(@Field("uid") String user_id,
                                          @Field("order_sn") String order_sn,
                                          @Field("refund_msg") String refund_msg);

    @FormUrlEncoded
    @POST("api/gourmet/wxPay")
    Observable<BaseResult<WXPayBean>> wxPay(@Field("uid") String user_id,
                                            @Field("sNo") String order_sn);

    @FormUrlEncoded
    @POST("api/gourmet/balancePay")
    Observable<BaseResult<Object>> accountPay(@Field("uid") String user_id,
                                              @Field("sNo") String order_sn);

    @FormUrlEncoded
    @POST("api/gourmet/addcomment")
    Observable<BaseResult<Object>> addComment(@Field("uid") String user_id,
                                              @Field("order_id") String order_id,
                                              @Field("merchants_id") String merchants_id,
                                              @Field("content") String content,
                                              @Field("stars") String stars,
                                              @Field("image") String image,
                                              @Field("dianzhan") int dianzhan);

    @FormUrlEncoded
    @POST("api/gourmet/comment")
    Observable<BaseResult<ListData<Review>>> getReviews(@Field("id") String id,
                                                        @Field("page") int page);

    @FormUrlEncoded
    @POST("api/gourmet/reserve")
    Observable<BaseResult<GetBookInfoResponse>> getBookInfo(@Field("uid") String uid,
                                                            @Field("merchant_id") String merchant_id);

    /**
     * 商户类型
     * @return
     */
    @POST("api/common/merchant_type")
    Observable<BaseResult<MerchantHomeTypeBean>> merchanttype();

}
