package com.ykh.yinmeng.ymykh2.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
     * File Description : String的替换，以达到保密效果
     *
     *根据需求改写 以下正则都是数字，手机号代码没写和身份证号一样
     */
    public class StringReplaceUtil {
        /**
         * 根据用户名的不同长度，来进行替换 ，达到保密效果
         *
         * @param userName
         *            用户名
         * @return 替换后的用户名
         */
        public static String userNameReplaceWithStar(String userName) {
            String userNameAfterReplaced = "";
            if (userName == null) {
                userName = "";
            }

            int nameLength = userName.length();
            System.out.println(nameLength);

            if (nameLength <= 1) {
                userNameAfterReplaced = "*";
            } else if (nameLength == 2) {
                userNameAfterReplaced = replaceAction(userName, "(?<=\\w{0})\\w(?=\\w{1})");
            } else if (nameLength >= 3 && nameLength <= 6) {
                userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{1})");
            } else if (nameLength == 7) {
                userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{2})");
            } else if (nameLength == 8) {
                userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{2})");
            } else if (nameLength == 9) {
                userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{3})");
            } else if (nameLength == 10) {
                userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{3})");
            } else if (nameLength >= 11) {
                userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{4})");
            }

            return userNameAfterReplaced;

        }

        /**
         * 实际替换动作
         *
         * @param username
         *            username
         * @param regular
         *            正则
         * @return
         */
        private static String replaceAction(String username, String regular) {
            return username.replaceAll(regular, "*");
        }

        /**
         * 身份证号替换，保留前四位和后四位
         *
         * 如果身份证号为空 或者 null ,返回null ；否则，返回替换后的字符串；
         *
         * @param idCard
         *            身份证号
         * @return
         */
        public static String idCardReplaceWithStar(String idCard) {

            if (idCard.isEmpty() || idCard == null) {
                return null;
            } else {
                return replaceAction(idCard, "(?<=\\d{4})\\d(?=\\d{4})");
            }
        }

        /**
         * 银行卡替换，保留后四位
         *
         * 如果银行卡号为空 或者 null ,返回null ；否则，返回替换后的字符串；
         *
         * @param bankCard
         *            银行卡号
         * @return
         */
        public static String bankCardReplaceWithStar(String bankCard) {

            if (bankCard.isEmpty() || bankCard == null) {
                return null;
            } else {
                return replaceAction(bankCard, "(?<=\\d{0})\\d(?=\\d{4})");
            }
        }

    /**
     * 电话号码替换，保留前三位和后四位
     * 如果电话号码为空 或者 null ,返回null ；否则，返回替换后的字符串；
     * @param mobiles
     * @return
     */
    public static String isMobileNum(String mobiles) {
        if (mobiles.isEmpty() || mobiles == null){
            return null;
        }else {
            return replaceAction(mobiles,"(?<=\\d{3})\\d(?=\\d{4})");
        }

    }

    /**
     * 电话号码替换，保留后四位
     * 如果电话号码为空 或者 null ,返回null ；否则，返回替换后的字符串；
     * @param mobiles
     * @return
     */
    public static String showMobile(String mobiles) {
        if (mobiles.isEmpty() || mobiles == null){
            return null;
        }else {
            return replaceAction(mobiles,"/\\d{7}(\\d{11})/");
        }

    }


    /**
     * java正则姓名加密，保留姓，名用*号代替
     * @param str
     * @return
     */
    public static String replaceNameX(String str) {
        String reg = ".{1}";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
            i++;
            if (i == 1)
                continue;
            m.appendReplacement(sb, "*");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 姓名加密
     * 
     * @param length
     * @return
     */
    public static String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

    }

