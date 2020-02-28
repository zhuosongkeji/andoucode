package com.zskjprojectj.andouclient.http

import com.google.gson.JsonElement
import com.zhuosongkj.android.library.model.BaseResult
import com.zhuosongkj.android.library.model.ListData
import com.zskjprojectj.andouclient.entity.*
import com.zskjprojectj.andouclient.entity.hotel.*
import com.zskjprojectj.andouclient.entity.mall.*
import com.zskjprojectj.andouclient.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    /**
     * 获取商城首页数据
     */
    @get:POST("api/goods/index")
    val mallInfo: Observable<BaseResult<MallHomeDataBean>>


    /**
     * 获取商城商品分类
     */
    @get:POST("api/goods/goods_cate")
    val mallGoodsCate: Observable<BaseResult<List<MallGoodsCateBean>>>


    /**
     * 获取支付方式
     */
    @get:POST("api/common/pay_ways")
    val mallPayWays: Observable<BaseResult<List<MallPayWaysBean>>>

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

    @get:POST("api/gourmet/delicious")
    val foodCategory: Observable<BaseResult<List<RestaurantCategory>>>

    /**
     * 查询
     */
    @FormUrlEncoded
    @POST("cook/query")
    fun getinfo(@Field("key") key: String, @Field("menu") menu: String): Observable<BaseResult<TestBean.ResultBean>>

    /**
     * 商城商品主页展示
     */
    @POST("api/goods/goods")
    @FormUrlEncoded
    fun mallDetailsShow(@Field("id") id: String?, @Field("uid") uid: String): Observable<BaseResult<MallGoodsDetailsDataBean>>

    /**
     * 获取商城商品分类
     */
    @POST("api/goods/cate")
    @FormUrlEncoded
    fun getMallGoodsCate(@Field("id") id: String): Observable<BaseResult<List<MallGoodsCateBean>>>


    /**
     * 商城产品列表
     */
    @POST("api/goods/good_list")
    @FormUrlEncoded
    fun mallGoodsList(@Field("keyword") keyword: String,
                      @Field("cate_id") uid: String?,
                      @Field("is_recommend") is_recommend: String?,
                      @Field("is_bargain") is_bargain: String?,
                      @Field("price_sort") price_sort: String?,
                      @Field("volume_sort") volume_sort: String?,
                      @Field("start_sort") start_sort: String?,
                      @Field("page") page: Int
    ): Observable<BaseResult<ListData<MallGoodsListBean>>>


    /**
     * 商城商品收藏/取消收藏
     */
    @POST("api/goods/collection")
    @FormUrlEncoded
    fun mallGoodsCollection(@Field("id") id: String?, @Field("uid") uid: String, @Field("token") token: String, @Field("type") type: String): Observable<BaseResult<Any>>

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
    fun mallgoodsfollow(@Field("id") id: String, @Field("uid") uid: String, @Field("token") token: String, @Field("type") type: String): Observable<BaseResult<Any>>

    /**
     * 商城商品详情
     */
    @POST("api/goods/details")
    @FormUrlEncoded
    fun mallDetails(@Field("id") id: String): Observable<BaseResult<MallDetailsBean>>

    /**
     * 商城商品评论
     */
    @POST("api/goods/comment")
    @FormUrlEncoded
    fun mallComment(@Field("id") id: String): Observable<BaseResult<List<MallCommentBean>>>

    /**
     * 商城商品规格
     */
    @POST("api/goods/specslist")
    @FormUrlEncoded
    fun buySpecification(@Field("id") id: String?): Observable<BaseResult<MallBuyBean>>


    /**
     * 购物车购买
     */
    @POST("api/order/add_order_car")
    @FormUrlEncoded
    fun OrderBuyCar(@Field("uid") uid: String,
                    @Field("token") token: String,
                    @Field("id") id: String): Observable<BaseResult<MallCarBean>>


    /**
     * 商城商户详情
     */
    @POST("api/merchant/merchant_goods")
    @FormUrlEncoded
    fun mallMerchant(@Field("id") id: String, @Field("uid") uid: String,
                     @Field("keyword") keyword: String,
                     @Field("type_id") type_id: String,
                     @Field("price_sort") price_sort: String,
                     @Field("volume_sort") volume_sort: String,
                     @Field("page") page: Int
    ): Observable<BaseResult<MallShoppingHomeBean>>

    /**
     * 商城立即购买
     */
    @POST("api/order/add_order")
    @FormUrlEncoded
    fun MallBuyNow(@Field("uid") uid: String,
                   @Field("token") token: String,
                   @Field("goods_id") goods_id: String?,
                   @Field("merchant_id") merchant_id: String?,
                   @Field("goods_sku_id") goods_sku_id: String?,
                   @Field("num") num: String): Observable<BaseResult<MallBuyNowBean>>

    /**
     * 商城购买结算页
     */
    @POST("api/order/settlement")
    @FormUrlEncoded
    fun MallSettlement(@Field("uid") uid: String,
                       @Field("token") token: String,
                       @Field("order_sn") order_sn: String): Observable<BaseResult<MallSettlementBean>>


    /**
     * 快递查询
     */
    @POST("api/order/express")
    @FormUrlEncoded
    fun CheckLogistics(@Field("uid") uid: String,
                       @Field("token") token: String,
                       @Field("express_id") express_id: String,
                       @Field("courier_num") courier_num: String): Observable<BaseResult<CheckLogisticsBean>>

    /**
     * 获取充值方式
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/wallet/payWays")
    fun getWalletPayWays(@Field("uid") uid: String, @Field("token") token: String): Observable<BaseResult<List<MallPayWaysBean>>>

    /**
     * 微信支付
     */
    @FormUrlEncoded
    @POST("api/order/pay")
    fun MallWXPayWays(@Field("uid") uid: String,
                      @Field("token") token: String,
                      @Field("sNo") sNo: String,
                      @Field("pay_id") pay_id: String,
                      @Field("is_integral") is_integral: String,
                      @Field("puzzle_id") puzzle_id: String,
                      @Field("open_join") open_join: String,
                      @Field("group_id") group_id: String
    ): Observable<BaseResult<WXPayBean>>

    /**
     * 微信充值
     *
     * @param uid
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("api/wallet/recharge")
    fun MallWXPayWaysrecharge(@Field("uid") uid: String,
                              @Field("token") token: String,
                              @Field("money") money: String,
                              @Field("mobile") mobile: String?,
                              @Field("method") method: String?
    ): Observable<BaseResult<WXPayBean>>

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
    fun vip_recharge(@Field("uid") uid: String,
                     @Field("token") token: String,
                     @Field("pay_id") pay_id: String
    ): Observable<BaseResult<WXPayBean>>

    /**
     * 新增收货地址
     */
    @POST("api/Usersaddress/address_add")
    @FormUrlEncoded
    fun addAddress(@Field("uid") uid: String,
                   @Field("token") token: String,
                   @Field("name") name: String,
                   @Field("mobile") mobile: String,
                   @Field("province_id") province_id: String,
                   @Field("city_id") city_id: String,
                   @Field("district_id") district_id: String,
                   @Field("address") address: String,
                   @Field("is_defualt") is_defualt: String): Observable<BaseResult<Any>>

    /**
     * 修改收货地址
     */
    @POST("api/Usersaddress/address_edit")
    @FormUrlEncoded
    fun editAddress(@Field("id") id: String,
                    @Field("uid") uid: String,
                    @Field("token") token: String,
                    @Field("name") name: String,
                    @Field("tel") mobile: String,
                    @Field("province_id") province_id: String,
                    @Field("city_id") city_id: String,
                    @Field("district_id") district_id: String,
                    @Field("address") address: String,
                    @Field("is_defualt") is_defualt: String): Observable<BaseResult<Any>>

    /**
     * 设置默认收货地址
     */
    @POST("api/Usersaddress/default")
    @FormUrlEncoded
    fun defaultAddress(@Field("id") id: String,
                       @Field("uid") uid: String,
                       @Field("token") token: String): Observable<BaseResult<Any>>

    /**
     * 添加购物车
     */
    @POST("api/cart/addcar")
    @FormUrlEncoded
    fun addCar(@Field("uid") uid: String,
               @Field("token") token: String,
               @Field("goods_id") goods_id: String?,
               @Field("merchant_id") merchant_id: String?,
               @Field("goods_sku_id") goods_sku_id: String?): Observable<BaseResult<Any>>

    /**
     * 购物车列表
     */
    @POST("api/cart/index")
    @FormUrlEncoded
    fun cart(@Field("uid") uid: String,
             @Field("token") token: String,
             @Field("page") page: Int
    ): Observable<BaseResult<ListData<CartItem>>>

    /**
     * 删除购物车
     */
    @POST("api/cart/delcar")
    @FormUrlEncoded
    fun delCart(@Field("uid") uid: String,
                @Field("token") token: String,
                @Field("id") id: String): Observable<BaseResult<Any>>

    /**
     * 修改购物车
     */
    @POST("api/cart/update_num")
    @FormUrlEncoded
    fun editCart(@Field("uid") uid: String,
                 @Field("token") token: String,
                 @Field("id") id: String,
                 @Field("type") type: String): Observable<BaseResult<Any>>

    /**
     * 登录
     */
    @POST("api/login/login_p")
    @FormUrlEncoded
    fun login(@Field("phone") uid: String,
              @Field("password") token: String): Observable<BaseResult<User>>

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    @POST("api/login/wxlogin")
    @FormUrlEncoded
    fun loginweixin(@Field("code") code: String): Observable<BaseResult<User>>

    /**
     * 发送验证码
     */
    @POST("api/login/send")
    @FormUrlEncoded
    fun sendCode(@Field("phone") uid: String,
                 @Field("type") type: String): Observable<BaseResult<Any>>

    /**
     * 验证码注册
     */
    @POST("api/login/reg_p")
    @FormUrlEncoded
    fun register(@Field("phone") uid: String,
                 @Field("password") type: String,
                 @Field("verify") verify: String): Observable<BaseResult<Any>>

    /**
     * 忘记密码
     */
    @POST("api/login/forget")
    @FormUrlEncoded
    fun forgetPassword(@Field("phone") uid: String,
                       @Field("new_password") type: String,
                       @Field("verify") verify: String): Observable<BaseResult<Any>>

    @POST("api/users/upmodel")
    @FormUrlEncoded
    fun upmodel(@Field("uid") uid: String,
                @Field("token") token: String,
                @Field("phone") phone: String,
                @Field("verify") verify: String): Observable<BaseResult<Any>>

    /**
     * 收货地址列表
     */
    @POST("api/Usersaddress/address")
    @FormUrlEncoded
    fun address(@Field("uid") uid: String,
                @Field("token") token: String): Observable<BaseResult<List<Address>>>

    /**
     * 删除收货地址
     */
    @POST("api/Usersaddress/address_del")
    @FormUrlEncoded
    fun delAddress(@Field("uid") uid: String,
                   @Field("token") token: String,
                   @Field("id") id: String): Observable<BaseResult<Any>>

    /**
     * 商户列表
     */
    @POST("api/merchant/merchants")
    fun merchants(): Observable<BaseResult<MerchantsResponse>>


    /**
     * 商户列表merchants two
     */
    @POST("api/merchant/merchants")
    @FormUrlEncoded
    fun merchants_two(
            @Field("merchant_type_id") merchant_type_id: String?,
            @Field("type") type: String?,
            @Field("page") page: Int): Observable<BaseResult<MerchantsResponse>>

    /**
     * 商户详情
     */
    @POST("api/merchant/merchant_goods")
    @FormUrlEncoded
    fun merchantDetail(@Field("id") id: String): Observable<BaseResult<Merchant>>

    /**
     * 余额明细
     */
    @POST("api/wallet/index")
    @FormUrlEncoded
    fun balanceDetail(@Field("uid") uid: String,
                      @Field("token") token: String): Observable<BaseResult<BalanceDetail>>

    /**
     * 余额明细
     */
    @POST("api/wallet/cash")
    @FormUrlEncoded
    fun cashDetail(@Field("uid") uid: String,
                   @Field("token") token: String): Observable<BaseResult<BalanceDetail>>

    /**
     * 余额提现
     */
    @POST("api/wallet/cash_withdrawal")
    @FormUrlEncoded
    fun cash(@Field("uid") uid: String,
             @Field("token") token: String,
             @Field("money") money: Float,
             @Field("phone") phone: String,
             @Field("num") num: String): Observable<BaseResult<Any>>

    /**
     * 订单列表
     */
    @POST("api/order/index")
    @FormUrlEncoded
    fun orderList(@Field("uid") uid: String,
                  @Field("token") token: String,
                  @Field("type") type: String,
                  @Field("page") page: Int
    ): Observable<BaseResult<ListData<Order>>>

    /**
     * 通知消息
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/index/notification_center")
    @FormUrlEncoded
    fun notificationcenter(@Field("uid") uid: String,
                           @Field("token") token: String): Observable<BaseResult<List<MymessageBean>>>

    /**
     * 通知消息
     *
     * @param id
     * @param uid
     * @return
     */
    @POST("api/index/information")
    @FormUrlEncoded
    fun information(@Field("id") id: String,
                    @Field("uid") uid: String): Observable<BaseResult<InformationBean>>

    /**
     * 订单详情
     */
    @POST("api/order/details")
    @FormUrlEncoded
    fun getOrderDetail(@Field("uid") uid: String,
                       @Field("token") token: String,
                       @Field("order_sn") order_sn: String,
                       @Field("did") did: String): Observable<BaseResult<OrderDetail>>


    /**
     * 确认收货
     */
    @POST("api/order/confirm")
    @FormUrlEncoded
    fun confirm(@Field("uid") uid: String,
                @Field("token") token: String,
                @Field("id") id: String): Observable<BaseResult<Any>>


    /**
     * 申请退款
     */
    @POST("api/refund/apply")
    @FormUrlEncoded
    fun mallrefund(@Field("uid") uid: String,
                   @Field("token") token: String,
                   @Field("order_goods_id") order_goods_id: String,
                   @Field("reason_id") reason_id: String,
                   @Field("content") content: String,
                   @Field("image") image: String): Observable<BaseResult<Any>>


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
    fun user_head(@Field("id") uid: String,
                  @Field("token") token: String,
                  @Field("name") name: String): Observable<BaseResult<Any>>

    /**
     * 添加商品评论
     */
    @POST("api/order/addcomment")
    @FormUrlEncoded
    fun addcomment(@Field("uid") uid: String,
                   @Field("token") token: String,
                   @Field("goods_id") goods_id: String,
                   @Field("order_id") order_id: String,
                   @Field("merchants_id") merchants_id: String,
                   @Field("content") content: String,
                   @Field("stars") stars: String): Observable<BaseResult<Any>>

    /**
     * 首页
     */
    @POST("api/index/index")
    fun index(): Observable<BaseResult<IndexHomeBean>>


    /**
     * 退款
     */
    @POST("api/refund/apply")
    @FormUrlEncoded
    fun refundapply(@Field("uid") uid: String,
                    @Field("token") token: String,
                    @Field("order_goods_id") order_goods_id: String,
                    @Field("reason_id") reason_id: String,
                    @Field("content") content: String): Observable<BaseResult<Any>>


    /**
     * 退货
     */
    @POST("api/refund/return_goods")
    @FormUrlEncoded
    fun refundgoods(@Field("uid") uid: String,
                    @Field("token") token: String,
                    @Field("order_goods_id") order_goods_id: String): Observable<BaseResult<Any>>

    /**
     * 退款原因
     */
    @POST("api/refund/reason")
    fun refundreason(): Observable<BaseResult<List<RefundReasonBean>>>


    /**
     * 酒店分类
     */
    @POST("api/hotel/cate")
    fun hotelCategory(): Observable<BaseResult<List<HotelCategoryBean>>>


    /**
     * 酒店商家列表
     */
    @POST("api/hotel/hotellist")
    @FormUrlEncoded
    fun hotelHomeList(@Field("keywords") keywords: String?,
                      @Field("star_price") star_price: String?,
                      @Field("end_price") end_price: String?,
                      @Field("stars_all") stars_all: String?,
                      @Field("type") type: String = "",
                      @Field("page") page: Int = 1)
            : Observable<BaseResult<ListData<HotelHomeBean>>>


    /**
     * 酒店搜索配置
     */
    @POST("api/hotel/condition")
    fun hotelSearchCondition(): Observable<BaseResult<HotelSearchConditionBean>>


    /**
     * list - 酒店商家详情
     */
    @POST("api/details/list")
    @FormUrlEncoded
    fun hotelDetails(@Field("uid") uid: String, @Field("id") id: String): Observable<BaseResult<HotelDetailsBean>>


    /**
     * list - 酒店商家房间列表
     */
    @POST("api/details/room_list")
    @FormUrlEncoded
    fun hotelDetailsHomeList(@Field("merchant_id") merchant_id: Int): Observable<BaseResult<HotelDetailReserveBean>>


    /**
     * list - 酒店商家房间详情
     */
    @POST("api/details/hotelSel")
    @FormUrlEncoded
    fun hotelHomeDetails(@Field("id") id: String): Observable<BaseResult<HotelHomeDetailsBean>>


    /**
     * list - 酒店商家评论列表
     */
    @POST("api/details/commnets")
    @FormUrlEncoded
    fun hotelDetailsCommentList(@Field("id") id: String, @Field("page") page: String): Observable<BaseResult<List<HotelDetailCommentBean>>>


    /**
     * list - 酒店结算页
     */
    @POST("api/htorder/settlement")
    @FormUrlEncoded
    fun hotelSettlement(@Field("uid") uid: String,
                        @Field("token") token: String,
                        @Field("start") start: String,
                        @Field("end") end: String,
                        @Field("id") id: String): Observable<BaseResult<HotelSettlementBean>>

    /**
     * list - 酒店预订
     */
    @POST("api/htorder/add_order")
    @FormUrlEncoded
    fun hotelOrder(@Field("uid") uid: String,
                   @Field("token") token: String,
                   @Field("id") id: String,
                   @Field("merchant_id") merchant_id: String,
                   @Field("start_time") start_time: String,
                   @Field("end_time") end_time: String,
                   @Field("real_name") real_name: String,
                   @Field("mobile") mobile: String,
                   @Field("num") num: String,
                   @Field("day_num") day_num: String,
                   @Field("pay_way") pay_way: String,
                   @Field("is_integral") is_integral: String): Observable<BaseResult<WXPayBean>>

    /**
     * list - 酒店订单
     */
    @POST("api/hotel/order")
    @FormUrlEncoded
    fun mehotelOrder(@Field("uid") uid: String,
                     @Field("token") token: String,
                     @Field("type") type: String,
                     @Field("page") page: Int): Observable<BaseResult<ListData<MeHotelBean>>>

    /**
     * list - 酒店预订详情
     */
    @POST("api/htorder/orderdatails")
    @FormUrlEncoded
    fun mehotelOrderDetails(@Field("uid") uid: String,
                            @Field("token") token: String,
                            @Field("book_sn") book_sn: String): Observable<BaseResult<MeHotelDetailsBean>>

    /**
     * 添加酒店评论
     */
    @POST("api/details/addcomment")
    @FormUrlEncoded
    fun addhotelcomment(@Field("uid") uid: String,
                        @Field("token") token: String,
                        @Field("goods_id") goods_id: String,
                        @Field("order_id") order_id: String,
                        @Field("merchants_id") merchants_id: String,
                        @Field("content") content: String,
                        @Field("stars") stars: String,
                        @Field("dianzhan") dianzhan: String): Observable<BaseResult<Any>>

    /**
     * 添加酒店点赞
     */
    @POST("api/users/fabulous")
    @FormUrlEncoded
    fun addhotelfabulous(@Field("uid") uid: String,
                         @Field("token") token: String,
                         @Field("id") id: String): Observable<BaseResult<Any>>


    /**
     * 酒店退款原因
     */
    @POST("api/htorder/refund_reason")
    @FormUrlEncoded
    fun hotelrefundreason(@Field("uid") uid: String,
                          @Field("token") token: String,
                          @Field("merchants_id") merchants_id: String): Observable<BaseResult<List<HotelrefundreasonBean>>>


    /**
     * 酒店退款
     */
    @POST("api/htorder/refund")
    @FormUrlEncoded
    fun hotelrefund(@Field("uid") uid: String,
                    @Field("token") token: String,
                    @Field("book_sn") book_sn: String,
                    @Field("refund_id") refund_id: String,
                    @Field("refund_msg") refund_msg: String): Observable<BaseResult<Any>>

    /**
     * 绑定手机号
     */
    @POST("api/login/bindmobile")
    @FormUrlEncoded
    fun bindlogin(@Field("phone") uid: String,
                  @Field("password") token: String,
                  @Field("verify") verify: String,
                  @Field("name") name: String,
                  @Field("openid") openid: String,
                  @Field("avator") avator: String): Observable<BaseResult<User>>

    /**
     * 个人中心
     */
    @POST("api/wallet/personal")
    @FormUrlEncoded
    fun getpersonal(@Field("uid") uid: String,
                    @Field("token") token: String): Observable<BaseResult<PersonalBean>>

    /**
     * 设置
     *
     * @param uid
     * @param token
     * @return
     */

    @POST("api/opinion/set")
    @FormUrlEncoded
    operator fun set(@Field("uid") uid: String,
                     @Field("token") token: String): Observable<BaseResult<SetBean>>

    /**
     * 浏览痕迹
     */
    @POST("api/users/merchant_record")
    @FormUrlEncoded
    fun merchantrecord(@Field("uid") uid: String,
                       @Field("token") token: String,
                       @Field("page") page: Int)
            : Observable<BaseResult<ListData<BrowsingBean>>>

    /**
     * 我的平台币
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/wallet/integral")
    @FormUrlEncoded
    fun integralDetail(@Field("uid") uid: String,
                       @Field("token") token: String): Observable<BaseResult<IntegralDetail>>

    /**
     * 我的收藏
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/collection")
    @FormUrlEncoded
    fun usercollection(@Field("uid") uid: String,
                       @Field("token") token: String): Observable<BaseResult<List<MycollectionBean>>>


    /**
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/follow")
    @FormUrlEncoded
    fun usersfollow(@Field("uid") uid: String,
                    @Field("token") token: String): Observable<BaseResult<List<MyFocusonBean>>>

    /**
     * 获取充值详细信息
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/wallet/rechar")
    @FormUrlEncoded
    fun walletrechar(@Field("uid") uid: String,
                     @Field("token") token: String): Observable<BaseResult<WalletrecharBean>>

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
    fun uploadMerchantsInfo(@Field("uid") uid: String,
                            @Field("token") token: String,
                            @Field("type_id") type_id: Int,
                            @Field("name") name: String,
                            @Field("user_name") user_name: String,
                            @Field("tel") tel: String,
                            @Field("province_id") province_id: Int,
                            @Field("city_id") city_id: Int,
                            @Field("area_id") area_id: Int,
                            @Field("address") address: String,
                            @Field("desc") desc: String,
                            @Field("banner_img") bannerImg: String,
                            @Field("logo_img") logo_img: String,
                            @Field("management_img") management_img: String): Observable<BaseResult<Any>>

    /**
     * 地址
     *
     * @return
     */
    @GET("api/common/district")
    fun districts(@Query("id") provinceId: Int? = null): Observable<BaseResult<List<District>>>

    /**
     * 图片上传
     *
     * @param uid
     * @param file
     * @return
     */
    @POST("api/modification/user_pictures")
    @FormUrlEncoded
    fun user_pictures(@Field("id") uid: String, @Field("token") token: String, @Field("avator") file: String): Observable<BaseResult<String>>

    /**
     * 图片上传
     *
     * @param uid
     * @param file
     * @return
     */
    @POST("api/goods/uploads")
    @Multipart
    fun uploadImg(@Part("uid") uid: RequestBody, @Part("token") token: RequestBody, @Part file: MultipartBody.Part): Observable<BaseResult<String>>

    /**
     * 邀请码
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/invitations")
    @FormUrlEncoded
    fun invitationsvip(@Field("uid") uid: String, @Field("token") token: String): Observable<BaseResult<InvitationBean>>

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
    fun binding(@Field("uid") uid: String, @Field("token") token: String, @Field("code") code: String): Observable<BaseResult<Any>>

    /**
     * 添加酒店点赞
     */
    @POST("api/opinion/index")
    @FormUrlEncoded
    fun opinionindex(@Field("uid") uid: String,
                     @Field("token") token: String,
                     @Field("content") content: String): Observable<BaseResult<Any>>

    /**
     * 酒店搜索配置
     */
    @POST("api/index/about")
    fun about(): Observable<BaseResult<AboutusBean>>

    /**
     * 添加酒店点赞
     */
    @POST("api/goods/quit")
    @FormUrlEncoded
    fun quit(@Field("uid") uid: String,
             @Field("token") token: String): Observable<BaseResult<Any>>

    /**
     * 会员规则
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/vip_rote")
    @FormUrlEncoded
    fun vip_rote(@Field("uid") uid: String,
                 @Field("token") token: String): Observable<BaseResult<ViproteBean>>

    /**
     * 红包领取
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/envelopes_add")
    @FormUrlEncoded
    fun envelopes_add(@Field("uid") uid: String,
                      @Field("token") token: String): Observable<BaseResult<Any>>

    /**
     * 判断是否是新用户
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("api/users/new_user")
    @FormUrlEncoded
    fun new_user(@Field("uid") uid: String,
                 @Field("token") token: String): Observable<BaseResult<NewuserBean>>

    @POST("api/users/envelopes")
    @FormUrlEncoded
    fun envelopes(@Field("uid") uid: String,
                  @Field("token") token: String): Observable<BaseResult<EnvelopesBean>>

    @FormUrlEncoded
    @POST("api/gourmet/list")
    fun getRestaurants(@Field("name") name: String? = null,
                       @Field("cate_id") id: String? = null,
                       @Field("sort") sort: String? = null,
                       @Field("page") page: Int = 1)
            : Observable<BaseResult<ListData<Restaurant>>>

    @FormUrlEncoded
    @POST("api/gourmet/details")
    fun getRestaurantDetail(@Field("uid") uid: String,
                            @Field("id") id: String): Observable<BaseResult<Restaurant>>

    @FormUrlEncoded
    @POST("api/gourmet/dishtype")
    fun getFoodCategory(@Field("merchants_id") merchants_id: String): Observable<BaseResult<List<FoodCategory>>>

    @FormUrlEncoded
    @POST("api/gourmet/booking")
    fun getCart(@Field("user_id") user_id: String,
                @Field("token") token: String,
                @Field("merchant_id") merchants_id: String): Observable<BaseResult<List<Food>>>

    @FormUrlEncoded
    @POST("api/gourmet/upd_foods")
    fun changeFoodCart(@Field("uid") user_id: String,
                       @Field("merchant_id") merchants_id: String,
                       @Field("id") id: String,
                       @Field("type") type: Int): Observable<BaseResult<Any>>

    @FormUrlEncoded
    @POST("api/gourmet/timely")
    fun payBill(@Field("uid") user_id: String,
                @Field("merchant_id") merchant_id: String,
                @Field("people") people: String,
                @Field("remark") remark: String,
                @Field("dinnertime") dinnertime: String,
                @Field("is_integral") is_integral: Int,
                @Field("method") method: Int): Observable<BaseResult<JsonElement>>

    @FormUrlEncoded
    @POST("api/gourmet/order")
    fun getOrderList(@Field("uid") user_id: String,
                     @Field("page") merchant_id: Int,
                     @Field("status") status: Int): Observable<BaseResult<RestaurantOrderIListResponse>>

    @FormUrlEncoded
    @POST("api/gourmet/order_details")
    fun getOrderDetail(@Field("uid") user_id: String,
                       @Field("id") id: String): Observable<BaseResult<RestaurantOrder>>

    @FormUrlEncoded
    @POST("api/gourmet/refund")
    fun refund(@Field("uid") user_id: String,
               @Field("order_sn") order_sn: String,
               @Field("refund_msg") refund_msg: String): Observable<BaseResult<Any>>

    @FormUrlEncoded
    @POST("api/gourmet/wxPay")
    fun wxPay(@Field("uid") user_id: String,
              @Field("sNo") order_sn: String): Observable<BaseResult<WXPayBean>>

    @FormUrlEncoded
    @POST("api/gourmet/balancePay")
    fun accountPay(@Field("uid") user_id: String,
                   @Field("sNo") order_sn: String): Observable<BaseResult<Any>>

    @FormUrlEncoded
    @POST("api/gourmet/addcomment")
    fun addComment(@Field("uid") user_id: String,
                   @Field("order_id") order_id: String,
                   @Field("merchants_id") merchants_id: String,
                   @Field("content") content: String,
                   @Field("stars") stars: String,
                   @Field("image") image: String,
                   @Field("dianzhan") dianzhan: Int): Observable<BaseResult<Any>>

    @FormUrlEncoded
    @POST("api/gourmet/comment")
    fun getReviews(@Field("id") id: String,
                   @Field("page") page: Int): Observable<BaseResult<ListData<Review>>>

    @FormUrlEncoded
    @POST("api/gourmet/reserve")
    fun getBookInfo(@Field("uid") uid: String,
                    @Field("merchant_id") merchant_id: String): Observable<BaseResult<GetBookInfoResponse>>

    @FormUrlEncoded
    @POST("api/goods/sec_list")
    fun miaoShaList(@Field("sec_hour") sec_hour: String,
                    @Field("page") page: Int): Observable<BaseResult<MiaoShaListResponse>>

    @GET("api/group/today_top")
    fun todayTop(): Observable<BaseResult<TodayTopResponse>>

    @GET("api/group/group_cate")
    fun pinTuanType(): Observable<BaseResult<List<PinTuan.PinTuanType>>>

    @GET("api/group/group_list/{cate_id}/{page}")
    fun pinTuanGoods(@Path("cate_id") cate_id: String,
                     @Path("page") page: Int): Observable<BaseResult<ListData<PinTuanGoods>>>

    /**
     * 商户类型
     *
     * @return
     */
    @POST("api/common/merchant_type")
    fun merchanttype(): Observable<BaseResult<MerchantHomeTypeBean>>


    /**
     * 团购明细
     */
    @GET("api/group/puzzle_detail/{id}")
    fun tuangouDetails(@Path("id") id: String?): Observable<BaseResult<PinTuanDetails>>


    @FormUrlEncoded
    @POST("api/goods/sec_details")
    fun miaoShaDetails(@Field("sec_id") sec_id: String?): Observable<BaseResult<MiaoShaDetails>>

    /**
     * goods - 开团/拼团订单
     */
    @FormUrlEncoded
    @POST("api/group/group_order")
    fun getGroupOrder(@Field("uid") uid: String,
                      @Field("token") token: String,
                      @Field("num") num: String,
                      @Field("puzzle_id") puzzle_id: String?,
                      @Field("open_join") open_join: Int,
                      @Field("group_id") group_id: String?
    ): Observable<BaseResult<GetGroupOrderResponse>>

    @FormUrlEncoded
    @POST("api/goods/sec_goods")
    fun miaosha(@Field("uid") uid: String,
                @Field("token") token: String,
                @Field("sec_id") sec_id: String?
    ): Observable<BaseResult<MiaoShaResponse>>

    @FormUrlEncoded
    @POST("Usersaddress/details")
    fun addressDetail(@Field("uid") uid: String,
                      @Field("token") token: String,
                      @Field("id") id: String?
    ): Observable<BaseResult<Address>>


    @FormUrlEncoded
    @POST("api/tieba/post")
    fun releaseTieBa(@Field("uid") user_id: String,
                     @Field("title") title: String,
                     @Field("content") content: String,
                     @Field("images") images: String? = null,
                     @Field("type_id") type_id: String,
                     @Field("contact_info") contact_info: String,
                     @Field("top_post") top_post: Boolean): Observable<BaseResult<Any>>

    @GET("api/tieba/types")
    fun tieBaTypes(): Observable<BaseResult<List<TieBaType>>>
}
