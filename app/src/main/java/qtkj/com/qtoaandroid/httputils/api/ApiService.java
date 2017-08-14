package qtkj.com.qtoaandroid.httputils.api;




import java.util.Map;

import okhttp3.RequestBody;
import qtkj.com.qtoaandroid.model.BaseModel;
import qtkj.com.qtoaandroid.model.Login;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by 1363655717 on 2017/3/21.
 */

public interface ApiService{

    /*登录*/
//    @POST("applogin.do")
//    Observable<ResponseBody> login(@Query("user_name") String user_name, @Query("key") String key, @Query("psw") String psw);
    /*登录*/
//    params.addBodyParameter("username", phone);
//        params.addBodyParameter("key", "csadfsadsadhsaufgsdiugfsdhfsiug");
//        params.addBodyParameter("uuid",uuid);
//        params.addBodyParameter("psw", MD5Encrypt.md5(pass));


    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseModel<Object>> executePost(
            @Path("url") String url,
            @FieldMap Map<String, String> maps);
    /**
     * 登录
     *
     * @param user_name 账号
     * @param key       安全秘钥
     * @param uuid      手机标示
     * @param psw       密码
     * @return
     */
//    @FormUrlEncoded
//    @POST("distributionuserlogin.do")
//    Observable<BaseModel<String>> login(@Field("username") String user_name, @Field("key") String key, @Field("uuid") String uuid, @Field("psw") String psw);

    /**
     * 注册
     *
     * @param phone
     * @param key
     * @param code
     * @param psw
     * @return
     */
//    @FormUrlEncoded
//    @POST("distributionappregest.do")
//    Observable<BaseModel<String>> registe(@Field("phone") String phone, @Field("key") String key, @Field("code") String code, @Field("psw") String psw);

    /**
     * 获取注册验证码
     *
     * @param phone
     * @param key
     * @param type
     * @return
     */
//    @FormUrlEncoded
//    @POST("regisproving.do")
//    Observable<BaseModel<String>> GetRegisterVerificationCode(@Field("phone") String phone, @Field("key") String key, @Field("type") String type);

    /**
     * 实名认证状态查询
     *
     * @param spread
     * @return
     */
//    @FormUrlEncoded
//    @POST("therealnameauthentication.do")
//    Observable<BaseModel<String>> RealNameAuthenticationAtate(@Field("spread") String spread);

    /**
     * 实名认证
     *
     * @param name 姓名
     * @param cardnumber 身份证号
     * @param spread  登录表示
     * @param filez 身份证正面照
     * @param filef 身份证反面照
     * @return
     */
//    @Multipart
//    @POST("distributionrealnameauthentication.do")
//    Observable<BaseModel<String>> RealNameAuthentication(@Part("name") RequestBody name, @Part("cardnumber") RequestBody cardnumber, @Part("spread") RequestBody spread, @Part("imag\"; filename=\"z.jpg") RequestBody filez, @Part("image\"; filename=\"f.jpg") RequestBody filef);

    /**
     * * 收款信息提交
     * @param spread  登录表示
     * @param ban_kusername 开户人姓名
     * @param sban_kcard  银行卡号
     * @param aff_iliatedbank  银行
     * @param ali_payname  支付宝姓名
     * @param ali_paynum   支付宝账号
     * @return
     */
//    @FormUrlEncoded
//    @POST("alipaydistributionorbankcard.do")
//    Observable<BaseModel<String>> GatheringInformationSet(@Field("spread") String spread
//            , @Field("ban_kusername") String ban_kusername, @Field("ban_kcard") String sban_kcard
//            , @Field("aff_iliatedbank") String aff_iliatedbank, @Field("ali_payname") String ali_payname
//            , @Field("ali_paynum") String ali_paynum);
    /**
     * 账户余额查询
     *
     * @param spread
     * @return
     */
//    @FormUrlEncoded
//    @POST("selectdistributionaccount.do")
//    Observable<BaseModel<String>> AccountBalanceQuery(@Field("spread") String spread);
    /**
     * 我的信息查询
     *
     * @param spread
     * @return
     */
//    @FormUrlEncoded
//    @POST("selectbydaid.do")
//    Observable<BaseModel<MyResponse>> AccountInformationQuery(@Field("userid") String spread);

    /**
     * 主页信息查询
     *
     * @param spread
     * @return
     */
//    @FormUrlEncoded
//    @POST("selectindex.do")
//    Observable<BaseModel<MainResponse>> MaihnQuery(@Field("spread") String spread);

    /**
     * 订单列表信息查询
     * @param spread  登录标示
     * @param what_to_do  做什么
     * @param pageindex  页码
     * @return
     */
//    @FormUrlEncoded
//    @POST("check_deliveryorder.do")
//    Observable<BaseModel<OrderResponse>> OrderQuery(@Field("spread") String spread, @Field("what_to_do") String what_to_do, @Field("pageindex") String pageindex);

    /**
     * 抢单订单详情查询
     * @param spread  登录标示
     * @param dl_key  订单id
     * @return
     */
//    @FormUrlEncoded
//    @POST("delivery_order_details.do")
//    Observable<BaseModel<GrabOrderDetailsResponse>> GrabOrderDetailsQuery(@Field("spread") String spread, @Field("dl_key") String dl_key);

    /**
     * 订单处理请求
     ** @param spread  登录标示
     * @param dl_key  订单id
     * @param what_to_do 处理
     * @return
     */
//    @FormUrlEncoded
//    @POST("order_operation.do")
//    Observable<BaseModel<String>> OrderProcessing(@Field("spread") String spread, @Field("dl_key") String dl_key, @Field("what_to_do") String what_to_do);
}
