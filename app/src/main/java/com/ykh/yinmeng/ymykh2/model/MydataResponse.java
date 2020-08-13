package com.ykh.yinmeng.ymykh2.model;

public class MydataResponse {

    /**
     * code : 1
     * msg : 下级会员数据
     * data : {"userInfo":{"id":35858,"ni_name":"亿卡汇","name":"刘莉妨","img":"/Public/headimg.jpg","password":"e99a18c428cb38d5f260853678922e03","epassword":null,"tel":"15938742156","addtime":null,"status":1,"money":"0.00","ljmoney":"0.00","coin":null,"inCoin":null,"pid1":34109,"pid2":0,"pid3":0,"cart":null,"kaihu":null,"ssx":null,"address":null,"erweima":null,"gz_time":null,"openid":null,"truename":"刘莉妨","dataall":0,"edittime":"0","is_shou":0,"is_fan":null,"is_dai":0,"did":0,"dtime":null,"invite":null,"age":null,"sex":null,"share_qrcode":"yYI7c41vl","is_real":1,"ctime":"2019-03-09 10:00:20","inRank":"0.00"},"rankInfo":{"id":34109,"ni_name":"温龑彪","name":"温彪","img":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190218/963e51cf79ad76e34b3ff0ee132d7174.jpg","password":"0659c7992e268962384eb17fafe88364","epassword":"123456","tel":"17603859599","addtime":1535768578,"status":1,"money":"540497.01","ljmoney":"125.78","coin":null,"inCoin":null,"pid1":0,"pid2":0,"pid3":0,"cart":null,"kaihu":null,"ssx":null,"address":null,"erweima":"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFd8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAydm1SRHhiUENjaDAxMDAwMHcwM0IAAgQC_IlbAwQAAAAA","gz_time":null,"openid":"oM6YRwE1P00y1nS2DrE0KlUebjuM","truename":null,"dataall":0,"edittime":"0","is_shou":1,"is_fan":1,"is_dai":0,"did":10112,"dtime":0,"invite":null,"age":0,"sex":0,"share_qrcode":"5t2edbnPA","is_real":1,"ctime":"2019-02-18 17:35:59","inRank":"0.00"},"extend":{"id":26,"uid":35858,"idNumber":"410426199306143040","idName":"刘莉妨","age":"19930614","sex":"女","nation":"汉","address":"河南省襄城县范湖乡台王村城刘","issuing":"襄城县公安局","frontUrl":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190309/319532c47694650c3f9db924bd3f400b.jpg","backUrl":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190309/4141b79cf8ff39149699d6129c67600d.jpg","cTime":"2019-03-09 10:00:20","uTime":null}}
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
         * userInfo : {"id":35858,"ni_name":"亿卡汇","name":"刘莉妨","img":"/Public/headimg.jpg","password":"e99a18c428cb38d5f260853678922e03","epassword":null,"tel":"15938742156","addtime":null,"status":1,"money":"0.00","ljmoney":"0.00","coin":null,"inCoin":null,"pid1":34109,"pid2":0,"pid3":0,"cart":null,"kaihu":null,"ssx":null,"address":null,"erweima":null,"gz_time":null,"openid":null,"truename":"刘莉妨","dataall":0,"edittime":"0","is_shou":0,"is_fan":null,"is_dai":0,"did":0,"dtime":null,"invite":null,"age":null,"sex":null,"share_qrcode":"yYI7c41vl","is_real":1,"ctime":"2019-03-09 10:00:20","inRank":"0.00"}
         * rankInfo : {"id":34109,"ni_name":"温龑彪","name":"温彪","img":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190218/963e51cf79ad76e34b3ff0ee132d7174.jpg","password":"0659c7992e268962384eb17fafe88364","epassword":"123456","tel":"17603859599","addtime":1535768578,"status":1,"money":"540497.01","ljmoney":"125.78","coin":null,"inCoin":null,"pid1":0,"pid2":0,"pid3":0,"cart":null,"kaihu":null,"ssx":null,"address":null,"erweima":"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFd8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAydm1SRHhiUENjaDAxMDAwMHcwM0IAAgQC_IlbAwQAAAAA","gz_time":null,"openid":"oM6YRwE1P00y1nS2DrE0KlUebjuM","truename":null,"dataall":0,"edittime":"0","is_shou":1,"is_fan":1,"is_dai":0,"did":10112,"dtime":0,"invite":null,"age":0,"sex":0,"share_qrcode":"5t2edbnPA","is_real":1,"ctime":"2019-02-18 17:35:59","inRank":"0.00"}
         * extend : {"id":26,"uid":35858,"idNumber":"410426199306143040","idName":"刘莉妨","age":"19930614","sex":"女","nation":"汉","address":"河南省襄城县范湖乡台王村城刘","issuing":"襄城县公安局","frontUrl":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190309/319532c47694650c3f9db924bd3f400b.jpg","backUrl":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190309/4141b79cf8ff39149699d6129c67600d.jpg","cTime":"2019-03-09 10:00:20","uTime":null}
         */

        private UserInfoBean userInfo;
        private RankInfoBean rankInfo;
        private ExtendBean extend;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public RankInfoBean getRankInfo() {
            return rankInfo;
        }

        public void setRankInfo(RankInfoBean rankInfo) {
            this.rankInfo = rankInfo;
        }

        public ExtendBean getExtend() {
            return extend;
        }

        public void setExtend(ExtendBean extend) {
            this.extend = extend;
        }

        public static class UserInfoBean {
            /**
             * id : 35858
             * ni_name : 亿卡汇
             * name : 刘莉妨
             * img : /Public/headimg.jpg
             * password : e99a18c428cb38d5f260853678922e03
             * epassword : null
             * tel : 15938742156
             * addtime : null
             * status : 1
             * money : 0.00
             * ljmoney : 0.00
             * coin : null
             * inCoin : null
             * pid1 : 34109
             * pid2 : 0
             * pid3 : 0
             * cart : null
             * kaihu : null
             * ssx : null
             * address : null
             * erweima : null
             * gz_time : null
             * openid : null
             * truename : 刘莉妨
             * dataall : 0
             * edittime : 0
             * is_shou : 0
             * is_fan : null
             * is_dai : 0
             * did : 0
             * dtime : null
             * invite : null
             * age : null
             * sex : null
             * share_qrcode : yYI7c41vl
             * is_real : 1
             * ctime : 2019-03-09 10:00:20
             * inRank : 0.00
             * level : 0
             */

            private int id;
            private String ni_name;
            private String name;
            private String img;
            private String password;
            private String epassword;
            private String tel;
            private String addtime;
            private int status;
            private String money;
            private String ljmoney;
            private String coin;
            private String inCoin;
            private int pid1;
            private int pid2;
            private int pid3;
            private String cart;
            private String kaihu;
            private String ssx;
            private String address;
            private String erweima;
            private String gz_time;
            private String openid;
            private String truename;
            private int dataall;
            private String edittime;
            private int is_shou;
            private int is_fan;
            private int is_dai;
            private int did;
            private int dtime;
            private Object invite;
            private String age;
            private String sex;
            private String share_qrcode;
            private int is_real;
            private String ctime;
            private String inRank;
            private int level;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNi_name() {
                return ni_name;
            }

            public void setNi_name(String ni_name) {
                this.ni_name = ni_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getEpassword() {
                return epassword;
            }

            public void setEpassword(String epassword) {
                this.epassword = epassword;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getLjmoney() {
                return ljmoney;
            }

            public void setLjmoney(String ljmoney) {
                this.ljmoney = ljmoney;
            }

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
            }

            public String getInCoin() {
                return inCoin;
            }

            public void setInCoin(String inCoin) {
                this.inCoin = inCoin;
            }

            public int getPid1() {
                return pid1;
            }

            public void setPid1(int pid1) {
                this.pid1 = pid1;
            }

            public int getPid2() {
                return pid2;
            }

            public void setPid2(int pid2) {
                this.pid2 = pid2;
            }

            public int getPid3() {
                return pid3;
            }

            public void setPid3(int pid3) {
                this.pid3 = pid3;
            }

            public String getCart() {
                return cart;
            }

            public void setCart(String cart) {
                this.cart = cart;
            }

            public String getKaihu() {
                return kaihu;
            }

            public void setKaihu(String kaihu) {
                this.kaihu = kaihu;
            }

            public String getSsx() {
                return ssx;
            }

            public void setSsx(String ssx) {
                this.ssx = ssx;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getErweima() {
                return erweima;
            }

            public void setErweima(String erweima) {
                this.erweima = erweima;
            }

            public String getGz_time() {
                return gz_time;
            }

            public void setGz_time(String gz_time) {
                this.gz_time = gz_time;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getTruename() {
                return truename;
            }

            public void setTruename(String truename) {
                this.truename = truename;
            }

            public int getDataall() {
                return dataall;
            }

            public void setDataall(int dataall) {
                this.dataall = dataall;
            }

            public String getEdittime() {
                return edittime;
            }

            public void setEdittime(String edittime) {
                this.edittime = edittime;
            }

            public int getIs_shou() {
                return is_shou;
            }

            public void setIs_shou(int is_shou) {
                this.is_shou = is_shou;
            }

            public int getIs_fan() {
                return is_fan;
            }

            public void setIs_fan(int is_fan) {
                this.is_fan = is_fan;
            }

            public int getIs_dai() {
                return is_dai;
            }

            public void setIs_dai(int is_dai) {
                this.is_dai = is_dai;
            }

            public int getDid() {
                return did;
            }

            public void setDid(int did) {
                this.did = did;
            }

            public int getDtime() {
                return dtime;
            }

            public void setDtime(int dtime) {
                this.dtime = dtime;
            }

            public Object getInvite() {
                return invite;
            }

            public void setInvite(Object invite) {
                this.invite = invite;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getShare_qrcode() {
                return share_qrcode;
            }

            public void setShare_qrcode(String share_qrcode) {
                this.share_qrcode = share_qrcode;
            }

            public int getIs_real() {
                return is_real;
            }

            public void setIs_real(int is_real) {
                this.is_real = is_real;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getInRank() {
                return inRank;
            }

            public void setInRank(String inRank) {
                this.inRank = inRank;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }
        }

        public static class RankInfoBean {
            /**
             * id : 34109
             * ni_name : 温龑彪
             * name : 温彪
             * img : http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190218/963e51cf79ad76e34b3ff0ee132d7174.jpg
             * password : 0659c7992e268962384eb17fafe88364
             * epassword : 123456
             * tel : 17603859599
             * addtime : 1535768578
             * status : 1
             * money : 540497.01
             * ljmoney : 125.78
             * coin : null
             * inCoin : null
             * pid1 : 0
             * pid2 : 0
             * pid3 : 0
             * cart : null
             * kaihu : null
             * ssx : null
             * address : null
             * erweima : https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFd8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAydm1SRHhiUENjaDAxMDAwMHcwM0IAAgQC_IlbAwQAAAAA
             * gz_time : null
             * openid : oM6YRwE1P00y1nS2DrE0KlUebjuM
             * truename : null
             * dataall : 0
             * edittime : 0
             * is_shou : 1
             * is_fan : 1
             * is_dai : 0
             * did : 10112
             * dtime : 0
             * invite : null
             * age : 0
             * sex : 0
             * share_qrcode : 5t2edbnPA
             * is_real : 1
             * ctime : 2019-02-18 17:35:59
             * inRank : 0.00
             */

            private int id;
            private String ni_name;
            private String name;
            private String img;
            private String password;
            private String epassword;
            private String tel;
            private String addtime;
            private int status;
            private String money;
            private String ljmoney;
            private String coin;
            private String inCoin;
            private int pid1;
            private int pid2;
            private int pid3;
            private String cart;
            private String kaihu;
            private String ssx;
            private String address;
            private String erweima;
            private String gz_time;
            private String openid;
            private String truename;
            private int dataall;
            private String edittime;
            private int is_shou;
            private int is_fan;
            private int is_dai;
            private int did;
            private int dtime;
            private Object invite;
            private String age;
            private String sex;
            private String share_qrcode;
            private int is_real;
            private String ctime;
            private String inRank;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNi_name() {
                return ni_name;
            }

            public void setNi_name(String ni_name) {
                this.ni_name = ni_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getEpassword() {
                return epassword;
            }

            public void setEpassword(String epassword) {
                this.epassword = epassword;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getLjmoney() {
                return ljmoney;
            }

            public void setLjmoney(String ljmoney) {
                this.ljmoney = ljmoney;
            }

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
            }

            public String getInCoin() {
                return inCoin;
            }

            public void setInCoin(String inCoin) {
                this.inCoin = inCoin;
            }

            public int getPid1() {
                return pid1;
            }

            public void setPid1(int pid1) {
                this.pid1 = pid1;
            }

            public int getPid2() {
                return pid2;
            }

            public void setPid2(int pid2) {
                this.pid2 = pid2;
            }

            public int getPid3() {
                return pid3;
            }

            public void setPid3(int pid3) {
                this.pid3 = pid3;
            }

            public String getCart() {
                return cart;
            }

            public void setCart(String cart) {
                this.cart = cart;
            }

            public String getKaihu() {
                return kaihu;
            }

            public void setKaihu(String kaihu) {
                this.kaihu = kaihu;
            }

            public String getSsx() {
                return ssx;
            }

            public void setSsx(String ssx) {
                this.ssx = ssx;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getErweima() {
                return erweima;
            }

            public void setErweima(String erweima) {
                this.erweima = erweima;
            }

            public String getGz_time() {
                return gz_time;
            }

            public void setGz_time(String gz_time) {
                this.gz_time = gz_time;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getTruename() {
                return truename;
            }

            public void setTruename(String truename) {
                this.truename = truename;
            }

            public int getDataall() {
                return dataall;
            }

            public void setDataall(int dataall) {
                this.dataall = dataall;
            }

            public String getEdittime() {
                return edittime;
            }

            public void setEdittime(String edittime) {
                this.edittime = edittime;
            }

            public int getIs_shou() {
                return is_shou;
            }

            public void setIs_shou(int is_shou) {
                this.is_shou = is_shou;
            }

            public int getIs_fan() {
                return is_fan;
            }

            public void setIs_fan(int is_fan) {
                this.is_fan = is_fan;
            }

            public int getIs_dai() {
                return is_dai;
            }

            public void setIs_dai(int is_dai) {
                this.is_dai = is_dai;
            }

            public int getDid() {
                return did;
            }

            public void setDid(int did) {
                this.did = did;
            }

            public int getDtime() {
                return dtime;
            }

            public void setDtime(int dtime) {
                this.dtime = dtime;
            }

            public Object getInvite() {
                return invite;
            }

            public void setInvite(Object invite) {
                this.invite = invite;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getShare_qrcode() {
                return share_qrcode;
            }

            public void setShare_qrcode(String share_qrcode) {
                this.share_qrcode = share_qrcode;
            }

            public int getIs_real() {
                return is_real;
            }

            public void setIs_real(int is_real) {
                this.is_real = is_real;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getInRank() {
                return inRank;
            }

            public void setInRank(String inRank) {
                this.inRank = inRank;
            }
        }

        public static class ExtendBean {
            /**
             * id : 26
             * uid : 35858
             * idNumber : 2324509875r
             * idName : 刘莉妨
             * age : 19930614
             * sex : 女
             * nation : 汉
             * address : 河南省襄城县范湖乡台王村城刘
             * issuing : 襄城县公安局
             * frontUrl : http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190309/319532c47694650c3f9db924bd3f400b.jpg
             * backUrl : http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/upload/images/20190309/4141b79cf8ff39149699d6129c67600d.jpg
             * cTime : 2019-03-09 10:00:20
             * uTime : null
             */

            private int id;
            private int uid;
            private String idNumber;
            private String idName;
            private String age;
            private String sex;
            private String nation;
            private String address;
            private String issuing;
            private String frontUrl;
            private String backUrl;
            private String cTime;
            private String uTime;

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

            public String getIdNumber() {
                return idNumber;
            }

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public String getIdName() {
                return idName;
            }

            public void setIdName(String idName) {
                this.idName = idName;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIssuing() {
                return issuing;
            }

            public void setIssuing(String issuing) {
                this.issuing = issuing;
            }

            public String getFrontUrl() {
                return frontUrl;
            }

            public void setFrontUrl(String frontUrl) {
                this.frontUrl = frontUrl;
            }

            public String getBackUrl() {
                return backUrl;
            }

            public void setBackUrl(String backUrl) {
                this.backUrl = backUrl;
            }

            public String getCTime() {
                return cTime;
            }

            public void setCTime(String cTime) {
                this.cTime = cTime;
            }

            public String getUTime() {
                return uTime;
            }

            public void setUTime(String uTime) {
                this.uTime = uTime;
            }
        }
    }
}
