package com.ykh.yinmeng.ymykh2.network;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.bean.IDCardBean;
import com.ykh.yinmeng.ymykh2.model.AddressResponse;
import com.ykh.yinmeng.ymykh2.model.BackgroundResponse;
import com.ykh.yinmeng.ymykh2.model.CardResponse;
import com.ykh.yinmeng.ymykh2.model.CreditsResponse;
import com.ykh.yinmeng.ymykh2.model.HomeResponse;
import com.ykh.yinmeng.ymykh2.model.IntegralcenterResponse;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.model.JifenItemgoodsResponse;
import com.ykh.yinmeng.ymykh2.model.JifenShouzhiResponse;
import com.ykh.yinmeng.ymykh2.model.JifenVIPResponse;
import com.ykh.yinmeng.ymykh2.model.JifenjinduResponse;
import com.ykh.yinmeng.ymykh2.model.KefuResponse;
import com.ykh.yinmeng.ymykh2.model.LogisticsResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.MyOrderResponse;
import com.ykh.yinmeng.ymykh2.model.MyTeamResponse;
import com.ykh.yinmeng.ymykh2.model.MydataResponse;
import com.ykh.yinmeng.ymykh2.model.MyhuiyuanResponse;
import com.ykh.yinmeng.ymykh2.model.NewXinyongkaResponse;
import com.ykh.yinmeng.ymykh2.model.OrderdikoujiluResponse;
import com.ykh.yinmeng.ymykh2.model.RebateResponse;
import com.ykh.yinmeng.ymykh2.model.JihuomingxiResponse;
import com.ykh.yinmeng.ymykh2.model.RedpacketResponse;
import com.ykh.yinmeng.ymykh2.model.ShanghuchaxunResponse;
import com.ykh.yinmeng.ymykh2.model.ShanghuzicaiResponse;
import com.ykh.yinmeng.ymykh2.model.SubmitOrderResponse;
import com.ykh.yinmeng.ymykh2.model.TixianjiluResponse;
import com.ykh.yinmeng.ymykh2.model.TuanduipaimingResponse;
import com.ykh.yinmeng.ymykh2.model.TuiguangshouyiResponse;
import com.ykh.yinmeng.ymykh2.model.WXResponse;
import com.ykh.yinmeng.ymykh2.model.XiajiorderResponse;
import com.ykh.yinmeng.ymykh2.model.XinyongkaResponse;
import com.ykh.yinmeng.ymykh2.model.XinyongkashenqingResponse;
import com.ykh.yinmeng.ymykh2.model.YaoqinghuiyuanResponse;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.ImageTools;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.Urls;


import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 2018/11/9 18:32
 * Description：
 */
public class ServerApi {
    /**登录*/
    public static <T>Observable<T> login(String username, String passwd) {
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("passwd", passwd);
        params.put("type", 1);
        return RxUtils.request(HttpMethod.POST, Urls.URL_LOGIN, type, params, null);
    }
    /**快捷登录*/
    public static <T>Observable<T> qLogin(String tel, String yzm) {
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        params.put("tel", tel);
        params.put("yzm", yzm);
        return RxUtils.request(HttpMethod.POST, Urls.URL_QLOGIN, type, params, headers);
    }
    /**注册*/
    public static <T>Observable<T> register(String yqtel, String tel,String password, String yzm) {
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpParams params = new HttpParams();
        if (yqtel != null) {
            params.put("yqtel", yqtel);
        }
        params.put("tel", tel);
        params.put("password", password);
        params.put("yzm", yzm);
        return RxUtils.request(HttpMethod.POST, Urls.URL_REGISTER, type, params, null);
    }
    /**忘记密码、重置登录密码*/
    public static <T>Observable<T> findPasswd(String tel,String password, String yzm) {
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpParams params = new HttpParams();
        params.put("tel", tel);
        params.put("password", password);
        params.put("yzm", yzm);
        return RxUtils.request(HttpMethod.POST, Urls.URL_FIND_PASSWD, type, params, null);
    }
    /**设置登录密码*/
    public static <T>Observable<T> setPasswd(String password) {
        Type type = new TypeToken<LzyResponse>() {}.getType();
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("password", password);
        return RxUtils.request(HttpMethod.POST, Urls.URL_SET_PASSWOD, type, params, headers);
    }
    /** 修改手机号*/
    public static <T>Observable<T> resetphone(String tel, String yzm) {
        Type type = new TypeToken<LzyResponse>() {}.getType();
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("tel", tel);
        params.put("yzm", yzm);
        return RxUtils.request(HttpMethod.POST, Urls.URL_RESET_PHONE, type, params, headers);
    }
    /*** 设置交易密码*/
    public static <T>Observable<T> setPayPwd(String tel,String yzm,String pwd) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("tel", tel);
        params.put("yzm", yzm);
        params.put("pwd", pwd);
        return RxUtils.request(HttpMethod.POST, Urls.URL_SETPAY_PWD, type, params, headers);
    }
    /*** 场景id 1,登陆 2 注册,3：修改(忘记)密码 5：更换新手机验证,6:设置支付密码 7. 商户拓展*/
    public static <T>Observable<T> getYzm(String phone, String code_id) {
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpParams params = new HttpParams();
        params.put("phone", phone);
        params.put("code_id", code_id);
        return RxUtils.request(HttpMethod.POST, Urls.URL_GETYZM, type, params, null);
    }
    /*** 8.1 获取收货地址列表*/
    public static <T>Observable<T> addressList(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<AddressResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST, Urls.URL_LIST_ADDRESS, type, params, headers);
    }
    /*** 8.4 删除收货地址*/
    public static <T>Observable<T> addressDel(String id){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("id",id);
        return RxUtils.request(HttpMethod.POST,Urls.URL_DEL_ADDRESS,type,params,headers);
    }
    /*** 8.2 添加收货地址（所有地区跳转的是可选择的省市区需要自己封装）*/
    public static <T>Observable<T> addressAdd(String shrName,String shrMobile,String shrProvince,String shrCity,String shrArea,String shrAddress,String isDefault){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("shrName",shrName);
        params.put("shrMobile",shrMobile);
        params.put("shrProvince",shrProvince);
        params.put("shrCity",shrCity);
        params.put("shrArea",shrArea);
        params.put("shrAddress",shrAddress);
        params.put("isDefault",isDefault);
        return RxUtils.request(HttpMethod.POST,Urls.URL_ADD_ADDRESS,type,params,headers);
    }
    /*** 8.3 编辑收货地址*/
    public static <T>Observable<T> addressEdit(String shrName,String shrMobile,String shrProvince,String shrCity,String shrArea,String shrAddress,String isDefault, String id){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("shrName",shrName);
        params.put("shrMobile",shrMobile);
        params.put("shrProvince",shrProvince);
        params.put("shrCity",shrCity);
        params.put("shrArea",shrArea);
        params.put("shrAddress",shrAddress);
        params.put("isDefault",isDefault);
        params.put("id",id);
        return RxUtils.request(HttpMethod.POST,Urls.URL_EDIT_ADDRESS,type,params,headers);
    }
    /*** 7.1 获取银行卡列表*/
    public static <T>Observable<T> bankcardList(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<CardResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_LIST_CARD,type,params,headers);
    }
    /*** 7.2 添加银行卡信息*/
    public static <T>Observable<T> bankcardAdd(String banksAccount,String banksNumber,String bankName,String branch,String status){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("bankName",bankName);
        params.put("banksNumber",banksNumber);
        params.put("branch",branch);
        params.put("status",status);
        params.put("banksAccount",banksAccount);
        return RxUtils.request(HttpMethod.POST,Urls.URL_ADD_CARD,type,params,headers);
    }
    /*** 7.3 编辑银行卡信息*/
    public static <T>Observable<T> bankcardEdit(String banksAccount,String banksNumber,String bankName,String branch,String status,String Id){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("bankName",bankName);
        params.put("banksNumber",banksNumber);
        params.put("branch",branch);
        params.put("status",status);
        params.put("banksAccount",banksAccount);
        params.put("id",Id);
        return RxUtils.request(HttpMethod.POST,Urls.URL_EDIT_CARD,type,params,headers);
    }
    /*** 7.4 删除银行卡信息*/
    public static <T>Observable<T> bankcarddel(String id){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("id",id);
        return RxUtils.request(HttpMethod.POST,Urls.URL_DEL_CARD,type,params,headers);
    }
    /*** 13 提现接口*/
    public static <T>Observable<T> drawmoney(String id,String money){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type typee = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("id",id);
        params.put("money",money);
        return RxUtils.request(HttpMethod.POST,Urls.URL_WITHDRAW,typee,params,headers);
    }
    /*** 13 提现记录接口*/
    public static <T>Observable<T> drawmoneyjilu(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type typee = new TypeToken<TixianjiluResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_TIXIAN_JILU,typee,params,headers);
    }
    /*** 13 订单抵扣记录接口*/
    public static <T>Observable<T> orderjilu(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type typee = new TypeToken<OrderdikoujiluResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_ORDER_JILU,typee,params,headers);
    }
    /*** 12.2 我的团队*/
    public static <T>Observable<T> myteam(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<MyTeamResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_TEAM_LIST,type,params,headers);
    }
    /*** 我的团队里面的 12.3 我的会员 */
    public static <T>Observable<T> myhuiyuan(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<MyhuiyuanResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_TEAM_MEMBER,type,params,headers);
    }

    /*** 我的团队里面的 12.4 团队排名*/
    public static <T>Observable<T> teamranking(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<TuanduipaimingResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_RANKING_TEAM,type,params,headers);
    }
    /*** 我的团队里面的 12.5 激活明细*/
    public static <T>Observable<T> activeLog() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<JihuomingxiResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        return RxUtils.request(HttpMethod.POST, Urls.URL_DETAIL_ACTIVATE, type, null, headers);
    }
    /**我的团队里面的 12.6 商户查询*/
    public static <T>Observable<T> business_query(String type,String keyword) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        Type typee = new TypeToken<ShanghuchaxunResponse>() {}.getType();
        HttpParams params = new HttpParams();
        params.put("type",type);
        params.put("keyword",keyword);
        return RxUtils.request(HttpMethod.POST, Urls.URL_BUSINESS_QUERY, typee, params, headers);
    }


    /**我的团队里面的 12.7 推广收益*/
    public static <T>Observable<T> getMonths(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<TuiguangshouyiResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_EARN_REBATE,type,params,headers);
    }

    /***  收益详情：12.8机器返利和交易返利*/
    public static <T>Observable<T> getrebate(String type){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type1 = new TypeToken<RebateResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("type",type);
        return RxUtils.request(HttpMethod.POST,Urls.URL_REBATE,type1,params,headers);
    }
    /***  收益详情：12.8机器返利和交易返利*/
    public static <T>Observable<T> cash(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type1 = new TypeToken<RebateResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_CASH,type1,params,headers);
    }
    /*** 11 商户拓展*/
    public static <T>Observable<T> businessexpand(String tel,String idName,String sn){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type typee = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("tel",tel);
        params.put("idName",idName);
        params.put("sn",sn);
        return RxUtils.request(HttpMethod.POST,Urls.URL_BUSINESS_TUOZHAN,typee,params,headers);
    }
    /*** 15 获取POS商品列表*/
    public static <T>Observable<T> shanghuzicai() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        Type type = new TypeToken<ShanghuzicaiResponse>() {}.getType();
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST, Urls.URL_BUSINESS_LIST, type, params, headers);
    }
    /*** 15.1  增加POS机关注度接口*/
    public static <T>Observable<T> POSAttention(String id) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpParams params = new HttpParams();
        params.put("id",id);
        return RxUtils.request(HttpMethod.POST, Urls.URL_BUSINESS_GUANZHUDU, type, params, headers);
    }
        /*** 上传身份证信息*/
    public static <T>Observable<T> uploadUserInfo(IDCardBean resultZM, IDCardBean resultFM) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("idNumber", resultZM.getIdNumber());
        params.put("idName", resultZM.getName());
        params.put("age", resultZM.getBirthday());
        params.put("sex", resultZM.getGender());
        params.put("nation", resultZM.getEthnic());
        params.put("address", resultZM.getAddress());
        params.put("issuing", resultFM.getIssueAuthority());
        params.put("frontUrl", resultZM.getUrl());
        params.put("backUrl", resultFM.getUrl());
        Log.i("身份证参数",
                "idNumber:"+resultZM.getIdNumber()+
                        "idName:"+resultZM.getName()+
                        "age:"+resultZM.getBirthday()+
                        "sex:"+resultZM.getGender()+
                        "nation:"+resultZM.getEthnic()+
                        "address:"+resultZM.getAddress()+
                        "issuing"+resultFM.getIssueAuthority()+
                        "正面："+resultZM.getUrl()+
                        "反面："+resultFM.getUrl()

                );
        return RxUtils.request(HttpMethod.POST, Urls.URL_USER_USERINFO, type, params, headers);
    }
    /*** 上传图片*/
    public static <T>Observable<T> uploadBase64Images(Bitmap bitmap) {
        String imgBase64 = ImageTools.Bitmap2StrByBase64(bitmap);
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("imgBase64", imgBase64);
        return RxUtils.request(HttpMethod.POST, Urls.URL_USER_IMG, type, params, headers);
    }
    /*** 9.1 获取订单列表*/
    /**
     *订单状态:0 待付款 1 已经支付 2 已发货,3,已收货 , 空是全部订单
     * @param
     * @param <T>
     * @return
     */
    public static <T>Observable<T> orderList( String status){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<MyOrderResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("status",status);
        return RxUtils.request(HttpMethod.POST,Urls.URL_LIST_ORDER,type,params,headers);
    }
    /*** 9.2 取消订单*/
    public static <T>Observable<T> orderdel(String id){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("id",id);
        return RxUtils.request(HttpMethod.POST,Urls.URL_ORDER_DEL,type,params,headers);
    }
    /*** 9.3 确认收货*/
    public static <T>Observable<T> orderConfirm(String out_trade_no){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("out_trade_no",out_trade_no);
        return RxUtils.request(HttpMethod.POST,Urls.URL_ORDER_OVER,type,params,headers);
    }
    /*** 14 物流信息*/
    public static <T>Observable<T> logistic(String ShipperCode,String LogisticCode){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LogisticsResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("ShipperCode",ShipperCode);
        params.put("LogisticCode",LogisticCode);
        return RxUtils.request(HttpMethod.POST,Urls.URL_ORDER_LOGISTIC,type,params,headers);
    }
    /**下级订单**/
    public static <T>Observable<T> lowerList(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<XiajiorderResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_LOWER_ORDER,type,params,headers);
    }
    /*** 确认交易密码（确认提交订单的时候需要输入交易密码的时候调用此接口）*/
    public static <T>Observable<T> confirmPayPwd(String pwd) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("pwd", pwd);
        return RxUtils.request(HttpMethod.POST, Urls.URL_PAY_PWD, type, params, headers);
    }
    /*** 9.4 下单*/
    public static <T>Observable<T> orderPlace(String gid,String num,String type,String shr_id,String off_money){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type typee = new TypeToken<SubmitOrderResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("gid",gid);
        params.put("num",num);
        params.put("type",type);
        params.put("shr_id",shr_id);
        params.put("off_money",off_money);
        return RxUtils.request(HttpMethod.POST,Urls.URL_PLACE_ORDER,typee,params,headers);
    }
    /*** 10.1 阿里支付*/
    public static <T>Observable<T> payAL(String id){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("no",id);
        return RxUtils.request(HttpMethod.POST,Urls.URL_PAY_ZFB,type,params,headers);
    }
    /*** 10.2 微信支付*/
    public static <T>Observable<T> Wxpay(String id){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<WXResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("no",id);
        return RxUtils.request(HttpMethod.POST,Urls.URL_PAY_WX,type,params,headers);
    }
    /**5.0 邀请会员(要生成二维码)*/
    public static <T>Observable<T> inviteMember(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<YaoqinghuiyuanResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        return RxUtils.request(HttpMethod.POST,Urls.URL_INVITE_MEMBER,type,null,headers);
    }
    /**首页*/
    public static <T>Observable<T> getIndex() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse<HomeResponse>>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        return RxUtils.request(HttpMethod.POST, Urls.URL_INDEX_INDEX, type, null, headers);
    }
    /** 15 获取用户数据(是否实名认证都可以)*/
    public static <T>Observable<T> getcenter() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<MydataResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        return RxUtils.request(HttpMethod.POST, Urls.URL_CENTER, type, null, headers);
    }
    /*** 4.0.5 更换头像*/
    public static <T>Observable<T> changeuserHeadImg(String userHeadImg){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("userHeadImg",userHeadImg);
        return RxUtils.request(HttpMethod.POST,Urls.URL_HEADIMG,type,params,headers);
    }
    /*** 1.5 账户余额*/
    public static <T>Observable<T> getBlance() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST, Urls.URL_GET_BLANCE, type, params, headers);
    }
    /**16 (旧接口)信用卡*/
    public static <T>Observable<T> xinyongka() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<XinyongkaResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        return RxUtils.request(HttpMethod.POST, Urls.URL_CARD_XINYONG, type, null, headers);
    }
    /**16 (新接口)信用卡*/
    public static <T>Observable<T> newxinyongka(String page) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<NewXinyongkaResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("page",page);
        return RxUtils.request(HttpMethod.POST, Urls.URL_CARD_NEWXINYONG, type, params, headers);
    }
    /**16 信用卡申请（创建订单）*/
    public static <T>Observable<T> xinyongkaorder(String product_id,String bank_id,String expect_amount,String amount) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<XinyongkashenqingResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("product_id",product_id);
        params.put("bank_id",bank_id);
        params.put("expect_amount",expect_amount);
        params.put("amount",amount);
        return RxUtils.request(HttpMethod.POST, Urls.URL_CARD_ORDER, type, params, headers);
    }
    /**16 信用卡申请*/
    public static <T>Observable<T> xinyongkashenqing(String tel,String bankId) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<XinyongkashenqingResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("tel",tel);
        params.put("bankId",bankId);
        return RxUtils.request(HttpMethod.POST, Urls.URL_CARD_XINYONGSHENQING, type, params, headers);
    }
    /**16.1 增加行用卡关注度接口*/
    public static <T>Observable<T> CardAttention(String id) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("id",id);
        return RxUtils.request(HttpMethod.POST, Urls.URL_CARD_ATTENTION, type, params, headers);
    }
    /**16 信用卡申请进度*/
    public static <T>Observable<T> xinyongkajindu(String tel) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("tel",tel);
        return RxUtils.request(HttpMethod.POST, Urls.URL_CARD_JINDU, type, params, headers);
    }

    /** 积分兑换 */

    /** 银行列表 */
    public static <T>Observable<T> creditsList() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<CreditsResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST, Urls.URL_CREDITS_LIST, type, params, headers);
    }
    /** 积分vip价格 */
    public static <T>Observable<T> vipprice() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<JifenVIPResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST, Urls.URL_VIP_PRICE, type, params, headers);
    }
    /** 客服微信 */
    public static <T>Observable<T> kefu() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<KefuResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST, Urls.URL_KEFU, type, params, headers);
    }
    /** 银行分类列表 */
    public static <T>Observable<T> banklist(String bankId) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<ItemcardResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("bankId",bankId);
        return RxUtils.request(HttpMethod.POST, Urls.URL_BANK_LIST, type, params, headers);
    }
    /** 分类商品列表 */
    public static <T>Observable<T> goodslist(String cateId) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<JifenItemgoodsResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("cateId",cateId);
        return RxUtils.request(HttpMethod.POST, Urls.URL_GOODS_LIST, type, params, headers);
    }
    /** vip充值 */
    public static <T>Observable<T> vipchongzhi(String level,String money) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<SubmitOrderResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("level",level);
        params.put("money",money);
        return RxUtils.request(HttpMethod.POST, Urls.URL_VIP_CHONGZHI, type, params, headers);
    }
    /** vip免费升级 */
    public static <T>Observable<T> vipfree(String type) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type typee = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("type",type);
        return RxUtils.request(HttpMethod.POST, Urls.URL_VIP_FREE, typee, params, headers);
    }
    /** 兑换表单提交 */
    public static <T>Observable<T> duihuantijiao(String gid,String sn,String converPic,String mark) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("gid",gid);
        params.put("sn",sn);
        params.put("converPic",converPic);
        params.put("mark",mark);
        return RxUtils.request(HttpMethod.POST, Urls.URL_DUIHUAN_TIJIAO, type, params, headers);
    }
    /** 收支明细  type   1购买vip返利，2兑换积分，3下级购买分润，4提现 */
    public static <T>Observable<T> shouzhi(String startDate,String endDate) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<JifenShouzhiResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        return RxUtils.request(HttpMethod.POST, Urls.URL_SHOUZHI, type, params, headers);
    }
    /** 审核进度 */
    public static <T>Observable<T> jindu(String startDate,String endDate) {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<JifenjinduResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        return RxUtils.request(HttpMethod.POST, Urls.URL_JINDU, type, params, headers);
    }
    /** 积分个人中心 */
    public static <T>Observable<T> integralcenter() {
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<IntegralcenterResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST, Urls.URL_INTEGRAL_CENTER, type, params, headers);
    }
    /*** 积分提现接口*/
    public static <T>Observable<T> jifenmoney(String id,String money){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type typee = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("id",id);
        params.put("money",money);
        return RxUtils.request(HttpMethod.POST,Urls.URL_INTEGRAL_TIXIAN,typee,params,headers);
    }


    /*** 支付宝提现接口*/
    public static <T>Observable<T> zhifubaomoney(String money,String timestamp,String mac){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("money",money);
        params.put("timestamp",timestamp);
        params.put("mac",mac);
        return RxUtils.request(HttpMethod.POST,Urls.URL_ZHIFUBAO_TIXIAN,type,params,headers);
    }

    /*** 支付宝绑定接口*/
    public static <T>Observable<T> zhifubao(String aliAccount,String aliName,String code){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("aliAccount",aliAccount);
        params.put("aliName",aliName);
        params.put("code",code);
        return RxUtils.request(HttpMethod.POST,Urls.URL_ZHIFUBAO_BANGDINHG,type,params,headers);
    }

    /*** 银行卡代付 */
    public static <T>Observable<T> carddrawmoney(String cardNo,String amount,String timestamp,String mac){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type = new TypeToken<LzyResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        params.put("cardNo",cardNo);
        params.put("amount",amount);
        params.put("timestamp",timestamp);
        params.put("mac",mac);
        return RxUtils.request(HttpMethod.POST,Urls.URL_CARD_DAIFU,type,params,headers);
    }


    /***  收益详情：红包Log*/
    public static <T>Observable<T> redpacket(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type1 = new TypeToken<RedpacketResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_REDPACKET,type1,params,headers);
    }
    /***  收益详情：红包Log*/
    public static <T>Observable<T> chairedpacket(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type1 = new TypeToken<RedpacketResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_CHAIREDPACKET,type1,params,headers);
    }

    /***  邀请好友 背景*/
    public static <T>Observable<T> background(){
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        Type type1 = new TypeToken<BackgroundResponse>() {}.getType();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", token);
        HttpParams params = new HttpParams();
        return RxUtils.request(HttpMethod.POST,Urls.URL_INDEX_BACKGROUND,type1,params,headers);
    }

}
