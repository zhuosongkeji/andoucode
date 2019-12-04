package com.zskjprojectj.andouclient.http;




import com.zskjprojectj.andouclient.entity.TestBean;

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
    Observable<BaseResult <TestBean.ResultBean>> getinfo(@Field("key") String key, @Field("menu") String menu);
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
