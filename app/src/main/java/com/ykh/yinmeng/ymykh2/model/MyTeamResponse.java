package com.ykh.yinmeng.ymykh2.model;

/**
 * Created At 2018/12/12 22:41.
 *
 * @author larry
 */
public class MyTeamResponse {


    /**
     * code : 1
     * msg : 我的团队
     * data : {"sumDeal":"706809.27","dayUser":0,"shopsCount":31,"zJqCount":0,"zJqsumCount":362,"info":1657}
     */

    private int code;
    private String msg;
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
         * sumDeal : 706809.27
         * dayUser : 0
         * shopsCount : 31
         * zJqCount : 0
         * zJqsumCount : 362
         * info : 1657
         */

        private String sumDeal;
        private int dayUser;
        private int shopsCount;
        private int zJqCount;
        private int zJqsumCount;
        private int info;

        public String getSumDeal() {
            return sumDeal;
        }

        public void setSumDeal(String sumDeal) {
            this.sumDeal = sumDeal;
        }

        public int getDayUser() {
            return dayUser;
        }

        public void setDayUser(int dayUser) {
            this.dayUser = dayUser;
        }

        public int getShopsCount() {
            return shopsCount;
        }

        public void setShopsCount(int shopsCount) {
            this.shopsCount = shopsCount;
        }

        public int getZJqCount() {
            return zJqCount;
        }

        public void setZJqCount(int zJqCount) {
            this.zJqCount = zJqCount;
        }

        public int getZJqsumCount() {
            return zJqsumCount;
        }

        public void setZJqsumCount(int zJqsumCount) {
            this.zJqsumCount = zJqsumCount;
        }

        public int getInfo() {
            return info;
        }

        public void setInfo(int info) {
            this.info = info;
        }
    }
}
