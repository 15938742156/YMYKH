package com.ykh.yinmeng.ymykh2.utils;

public class Urls {
    public static final String SERVER = "http://app.ykh9.com/index/";
//    public static final String SERVER1 = "https://www.ymvpos.com/index/";
    public static final String URL_INDEX_VERSION = SERVER + "api/androidVersion";
    public static final String URL_VERSION_DOWNLOAD = "https://ldymapp.oss-cn-zhangjiakou.aliyuncs.com/app/Xinhui.apk";

    //账户密码登录
    public static final String URL_LOGIN = SERVER + "login/login";
    //短信快捷登录
    public static final String URL_QLOGIN = SERVER + "login/qLogin";
    //注册
    public static final String URL_REGISTER = SERVER + "login/appRegister";
    //获取短信验证码
    public static final String URL_GETYZM = SERVER + "index/getYzm";
    //忘记密码(重置密码)
    public static final String URL_FIND_PASSWD = SERVER + "login/findPasswd";
    //更换手机号
    public static final String URL_RESET_PHONE = SERVER + "user/mobileVerify";
    //设置登录密码
    public static final String URL_SET_PASSWOD = SERVER + "login/setPasswod";
    //获取地址列表
    public static final String URL_LIST_ADDRESS = SERVER + "distribution/index";
    //删除地址
    public static final String URL_DEL_ADDRESS = SERVER + "distribution/delShr";
    //添加地址
    public static final String URL_ADD_ADDRESS = SERVER + "distribution/addShr";
    //编辑更新地址
    public static final String URL_EDIT_ADDRESS = SERVER + "distribution/editShr";
    //获取银行卡列表
    public static final String URL_LIST_CARD = SERVER + "bank/getUserBank";
    //添加银行卡信息
    public static final String URL_ADD_CARD = SERVER + "bank/addUserBank";
    //编辑银行卡信息
    public static final String URL_EDIT_CARD = SERVER + "bank/updateUserBank";
    //删除银行卡信息
    public static final String URL_DEL_CARD = SERVER + "bank/delUserBank";
    //提现
    public static final String URL_WITHDRAW = SERVER + "deposit/extract";
    //提现记录
    public static final String URL_TIXIAN_JILU = SERVER + "deposit/extractLst";
    //订单抵扣记录
    public static final String URL_ORDER_JILU = SERVER + "deposit/composition";
    //我的团队
    public static final String URL_TEAM_LIST = SERVER + "Personal_data/index";
    //团队排名
    public static final String URL_RANKING_TEAM = SERVER + "personal_data/getAPPTop";
    //激活明细
    public static final String URL_DETAIL_ACTIVATE = SERVER + "personal_data/activeLog";
    //商户查询数据获取
    public static final String URL_BUSINESS_QUERY = SERVER + "award/inquire";
    //我的会员
    public static final String URL_TEAM_MEMBER = SERVER + "Personal_data/getLowUser";
    //推广收益
    public static final String URL_EARN_REBATE = SERVER + "Personal_data/getMonths";
    //机器返利&交易返利（type:1.机器返利; 2.交易返润）
    public static final String URL_REBATE = SERVER + "pos_shop/getMoneyLog";
    //押金退还接口
    public static final String URL_CASH = SERVER + "pos_shop/cash";
    //红包返利接口
    public static final String URL_REDPACKET = SERVER + "Incentive/envelopes";
    //红包返利接口
    public static final String URL_CHAIREDPACKET = SERVER + "Incentive/giveEnvelopes";
    //商户拓展
    public static final String URL_BUSINESS_TUOZHAN = SERVER + "user_regulate/addUser";
    //商户自采
    public static final String URL_BUSINESS_LIST = SERVER + "Pos_shop/index";
    //增加POS机关注度接口
    public static final String URL_BUSINESS_GUANZHUDU = SERVER + "api/updateNum";
    //上传实名认证信息
    public static final String URL_USER_USERINFO = SERVER + "user/userInfo";
    //图片上传
    public static final String URL_USER_IMG = SERVER + "upload/uploadBase64Images";
    //获取订单列表
    public static final String URL_LIST_ORDER = SERVER + "Pos_order/orderLst";
    //取消订单
    public static final String URL_ORDER_DEL = SERVER + "Pos_order/del";
    //确认收货
    public static final String URL_ORDER_OVER = SERVER + "Pos_order/receiveGoods";
    //下单
    public static final String URL_PLACE_ORDER = SERVER + "Pos_order/submit";
    //物流信息
    public static final String URL_ORDER_LOGISTIC = SERVER + "pos_order/getOrderTraces";
    //获取下级订单
    public static final String URL_LOWER_ORDER = SERVER + "Pos_order/lowerOrder";
    //邀请会员
    public static final String URL_INVITE_MEMBER = SERVER + "qrcode/getUserShareQrcode";
    //邀请好友 背景
    public static final String URL_INDEX_BACKGROUND = SERVER + "index/inviteBackground";
    //首页页面数据获取
    public static final String URL_INDEX_INDEX = SERVER + "index/index";
    //获取用户数据
    public static final String URL_CENTER = SERVER + "personal_data/center";
    //更换头像
    public static final String URL_HEADIMG = SERVER + "user/modifyUserHead";
    //账户余额
    public static final String URL_GET_BLANCE = SERVER + "index/getBlance";
    //阿里支付
    public static final String URL_PAY_ZFB = SERVER + "pay/zfb";
    //微信支付
    public static final String URL_PAY_WX = SERVER + "Wxpay/pay";
    //确认六位数字支付密码
    public static final String URL_PAY_PWD = SERVER + "user/usePay";
    //设置交易密码密码
    public static final String URL_SETPAY_PWD = SERVER + "user/setPayPwd";
    ////信用卡(接口)
    public static final String URL_CARD_XINYONG = SERVER + "pos_shop/card";//信用卡(旧接口)
    ////信用卡(接口)
    public static final String URL_CARD_NEWXINYONG = SERVER + "cardapi/cardLst";//信用卡(新接口)
    ////信用卡(接口)创建订单
    public static final String URL_CARD_ORDER = SERVER + "cardapi/createOrder";//信用卡(新接口)

    public static final String URL_CARD_XINYONGSHENQING = SERVER + "card_log/bankPlan";//信用卡申请(旧接口)
    public static final String URL_CARD_ATTENTION = SERVER + "api/updateCardNum";//信用卡关注度
    public static final String URL_CARD_JINDU = SERVER + "card_log/userOrder";//信用卡申请进度

    //积分兑换

    //银行列表
    public static final String URL_CREDITS_LIST = SERVER + "Integral/bankLst";
    //积分vip价格
    public static final String URL_VIP_PRICE = SERVER + "Integral/inteGoods";
    //客服微信
    public static final String URL_KEFU = SERVER + "Integral/kefu";
    //银行分类列表
    public static final String URL_BANK_LIST = SERVER + "Integral/bankCate";
    //分类商品列表
    public static final String URL_GOODS_LIST = SERVER + "Integral/bankGoods";
    //vip充值
    public static final String URL_VIP_CHONGZHI = SERVER + "Integral/submit";
    //vip免费升级
    public static final String URL_VIP_FREE = SERVER + "Integral/freeUpdate";
    //兑换表单提交
    public static final String URL_DUIHUAN_TIJIAO = SERVER + "Integral/conversionSub";
    //收支明细  type   1购买vip返利，2兑换积分，3下级购买分润，4提现
    public static final String URL_SHOUZHI = SERVER + "Integral/financeLog";
    //审核进度
    public static final String URL_JINDU = SERVER + "Integral/reviewPro";
    //积分个人中心
    public static final String URL_INTEGRAL_CENTER = SERVER + "Integral/center";
    //积分提现
    public static final String URL_INTEGRAL_TIXIAN = SERVER + "Deposit/extractCoin";

    //支付宝提现
    public static final String URL_ZHIFUBAO_TIXIAN = SERVER + "deposit/userWithdraw";
    //支付宝绑定
    public static final String URL_ZHIFUBAO_BANGDINHG = SERVER + "deposit/bindUser";
    //银行卡代付
    public static final String URL_CARD_DAIFU = SERVER + "Delegatepayfront/deposit";



    //H5静态页面0
    public static final String URL_PROTOCOL_SHIMING = "http://app.ykh9.com/ymapp/realName.html";//实名认证协议
    public static final String URL_PROTOCOL_YINSI = "http://app.ykh9.com/ymapp/privacy.html";//隐私保护协议
    public static final String URL_POS_DETIL = "http://app.ykh9.com/wap/goods/detail";//商品详情
    public static final String URL_SHOUYI_DETIL = "http://app.ykh9.com/ymapp/pattern.html";//推广模式
    public static final String URL_AOUTMY = "http://app.ykh9.com/ymapp/aboutUs.html";//关于我们
    public static final String URL_JIFEN_VIP = "http://app.ykh9.com/wap/news/privilege.html";//积分专享特权
}
