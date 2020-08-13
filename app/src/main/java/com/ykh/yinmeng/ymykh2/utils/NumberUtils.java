package com.ykh.yinmeng.ymykh2.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2018/11/13 14:59
 * Description：
 */
public class NumberUtils {
    /**
     * 正则表达式：验证码
     */
    private static final String REGEX_PASSWORD = "(?<![0-9])([0-9]{6})(?![0-9])";

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[9]))\\d{8}$";
//    private static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
//    String str = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$";
//    String str = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[9]))\\d{8}$";
    /**
     * 校验验证码
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isYzm(String mobile) {
        return Pattern.matches(REGEX_PASSWORD, mobile);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPhoneNumber(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 判断传入的字符串是否是一个手机号码
     *
     * @param strPhone
     * @return
     */
    public static boolean isMobile(String strPhone) {

//        String str = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$";
        String str = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(strPhone);
        return m.find();
    }

    public static boolean isPassword(Context context, String password) {
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(context, "请输入密码",Toast.LENGTH_SHORT);
            return false;
        }else if (!isLetterDigit(password)) {
            ToastUtils.showToast(context, "至少包含字母和数字",Toast.LENGTH_SHORT);
            return false;
        } else if (password.length() < 6 || password.length() > 12) {
            ToastUtils.showToast(context, "请输入6-12位密码", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    /**
     * 校验密码
     * @param password
     * @return
     */
    public static boolean isLengthRight(String password) {
        String str = "$pattern = '/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/'";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.find();
    }

    /**
     * 规则1：至少包含大小写字母及数字中的一种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        boolean isLetterOrDigit = false;//定义一个boolean值，用来表示是否包含字母或数字
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetterOrDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isLetterOrDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isLetterOrDigit && str.matches(regex);
        return isRight;
    }

    /**
     * 规则2：至少包含大小写字母及数字中的两种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//是否包含数字
        boolean isLetter = false;//是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     * 规则3：必须同时包含字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        boolean isDigit = false;//是否包含数字
        boolean isLowerCase = false;//是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
    }

    /**
     *   判断EditText输入的数字、中文还是字母方法
     */
    public static void whatIsInput(Context context, String txt) {

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        if (m.matches()) {
            Toast.makeText(context, "输入的是数字", Toast.LENGTH_SHORT).show();
        }
        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(txt);
        if (m.matches()) {
            Toast.makeText(context, "输入的是字母", Toast.LENGTH_SHORT).show();
        }
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(txt);
        if (m.matches()) {
            Toast.makeText(context, "输入的是汉字", Toast.LENGTH_SHORT).show();
        }
    }
}
