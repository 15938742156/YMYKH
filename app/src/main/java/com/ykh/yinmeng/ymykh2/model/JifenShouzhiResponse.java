package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class JifenShouzhiResponse {

    /**
     * code : 1
     * msg : 收支明细
     * data : {"total":5,"per_page":15,"current_page":1,"last_page":1,"data":[{"id":9,"uid":10112,"desc":"兑换积分收益","type":2,"type_alias_id":1029,"symbol":1,"pidLevel":0,"gid":1,"num":"40.00","createtime":"2019-05-16 16:07:51","goodsName":"100元唯品会券","tel":null,"name":null,"level":null,"coin":100000,"title":"招商银行"}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 5
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":9,"uid":10112,"desc":"兑换积分收益","type":2,"type_alias_id":1029,"symbol":1,"pidLevel":0,"gid":1,"num":"40.00","createtime":"2019-05-16 16:07:51","goodsName":"100元唯品会券","tel":null,"name":null,"level":null,"coin":100000,"title":"招商银行"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 9
             * uid : 10112
             * desc : 兑换积分收益
             * type : 2
             * type_alias_id : 1029
             * symbol : 1
             * pidLevel : 0
             * gid : 1
             * num : 40.00
             * createtime : 2019-05-16 16:07:51
             * goodsName : 100元唯品会券
             * tel : null
             * name : null
             * level : null
             * coin : 100000
             * title : 招商银行
             */

            private int id;
            private int uid;
            private String desc;
            private int type;
            private int type_alias_id;
            private int symbol;
            private String pidLevel;
            private int gid;
            private String num;
            private String createtime;
            private String goodsName;
            private String tel;
            private String name;
            private int level;
            private int coin;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType_alias_id() {
                return type_alias_id;
            }

            public void setType_alias_id(int type_alias_id) {
                this.type_alias_id = type_alias_id;
            }

            public int getSymbol() {
                return symbol;
            }

            public void setSymbol(int symbol) {
                this.symbol = symbol;
            }

            public String getPidLevel() {
                return pidLevel;
            }

            public void setPidLevel(String pidLevel) {
                this.pidLevel = pidLevel;
            }

            public int getGid() {
                return gid;
            }

            public void setGid(int gid) {
                this.gid = gid;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
