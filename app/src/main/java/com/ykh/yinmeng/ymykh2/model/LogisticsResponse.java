package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class LogisticsResponse {

    /**
     * LogisticCode : 51342368808542
     * ShipperCode : HTKY
     * Traces : [{"AcceptStation":"郑州市【东区十部】，【刘文杰/17638917220】已揽收","AcceptTime":"2018-12-04 18:45:29"},{"AcceptStation":"到郑州市【郑州转运中心】","AcceptTime":"2018-12-04 22:19:35"},{"AcceptStation":"郑州市【郑州转运中心】，正发往【武陟】","AcceptTime":"2018-12-05 23:21:59"},{"AcceptStation":"到焦作市【武陟】","AcceptTime":"2018-12-06 08:31:03"},{"AcceptStation":"焦作市【武陟】，【城西陈涛/18603911982】正在派件","AcceptTime":"2018-12-06 08:32:03"},{"AcceptStation":"焦作市【武陟】，东大街代理点18603911982 已签收","AcceptTime":"2018-12-06 18:00:43"}]
     * State : 3
     * EBusinessID : 1397576
     * Reason : 暂无轨迹信息
     * Success : true
     */

    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String EBusinessID;
    private String Reason;
    private boolean Success;
    private List<TracesBean> Traces;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }
    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean {
        /**
         * AcceptStation : 郑州市【东区十部】，【刘文杰/17638917220】已揽收
         * AcceptTime : 2018-12-04 18:45:29
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}
