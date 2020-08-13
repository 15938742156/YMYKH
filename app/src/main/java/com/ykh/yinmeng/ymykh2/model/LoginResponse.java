package com.ykh.yinmeng.ymykh2.model;

import com.google.gson.annotations.SerializedName;

/**
 * 2018/11/10 16:50
 * Description：
 */
public class LoginResponse {

    /**
     * code : 1
     * msg : 登陆成功
     * data : {"token":"pwmpF2de2S","user_id":10019}
     */
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : pwmpF2de2S
         * user_id : 10019
         * "isReal":1,
         * "jifenlevel":2,
         * "aliAccount":"15938742156"
         */

        private String token;
        private int user_id;
        private int isReal;
        private int jifenlevel;
        private String aliAccount;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIsReal() {
            return isReal;
        }

        public void setIsReal(int isReal) {
            this.isReal = isReal;
        }

        public int getJifenlevel() {
            return jifenlevel;
        }

        public void setJifenlevel(int jifenlevel) {
            this.jifenlevel = jifenlevel;
        }

        public String getAliAccount() {
            return aliAccount;
        }

        public void setAliAccount(String aliAccount) {
            this.aliAccount = aliAccount;
        }
    }
}
