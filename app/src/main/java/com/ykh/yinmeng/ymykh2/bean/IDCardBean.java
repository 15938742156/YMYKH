package com.ykh.yinmeng.ymykh2.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.baidu.ocr.sdk.model.IDCardResult;


/**
 * Created At 2018/11/25 23:17.
 *
 * @author Angel
 */
public class IDCardBean implements Parcelable {
    private int direction;
    private int wordsResultNumber;
    private String address = null;
    private String idNumber = null;
    private String birthday = null;
    private String name = null;
    private String gender = null;
    private String ethnic = null;
    private String idCardSide;
    private String riskType;
    private String imageStatus;
    private String signDate = null;
    private String expiryDate = null;
    private String issueAuthority = null;
    private String url = null;

    public IDCardBean() {

    }

    public IDCardBean(IDCardResult result) {
        this.direction = result.getDirection();
        this.wordsResultNumber = result.getWordsResultNumber();
        this.address = result.getAddress() == null ? null : result.getAddress().getWords();
        this.idNumber = result.getIdNumber() == null ? null : result.getIdNumber().getWords();
        this.birthday = result.getBirthday() == null ? null : result.getBirthday().getWords();
        this.name = result.getName() == null ? null : result.getName().getWords();
        this.gender = result.getGender() == null ? null : result.getGender().getWords();
        this.ethnic = result.getEthnic() == null ? null : result.getEthnic().getWords();
        this.idCardSide = result.getIdCardSide();
        this.riskType = result.getRiskType();
        this.imageStatus = result.getImageStatus();
        this.signDate = result.getSignDate() == null ? null : result.getSignDate().getWords();
        this.expiryDate = result.getExpiryDate() == null ? null : result.getExpiryDate().getWords();
        this.issueAuthority = result.getIssueAuthority() == null ? null : result.getIssueAuthority().getWords();
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWordsResultNumber() {
        return this.wordsResultNumber;
    }

    public void setWordsResultNumber(int wordsResultNumber) {
        this.wordsResultNumber = wordsResultNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnic() {
        return this.ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getIdCardSide() {
        return this.idCardSide;
    }

    public void setIdCardSide(String idCardSide) {
        this.idCardSide = idCardSide;
    }

    public String getSignDate() {
        return this.signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIssueAuthority() {
        return this.issueAuthority;
    }

    public void setIssueAuthority(String issueAuthority) {
        this.issueAuthority = issueAuthority;
    }

    public String getRiskType() {
        return this.riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getImageStatus() {
        return this.imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        if (TextUtils.isEmpty(this.idCardSide)) {
            return "";
        } else if (this.idCardSide.equals("front")) {
            return "IDCardResult front{direction=" + this.direction + ", wordsResultNumber=" + this.wordsResultNumber + ", address=" + this.address + ", idNumber=" + this.idNumber + ", birthday=" + this.birthday + ", name=" + this.name + ", gender=" + this.gender + ", ethnic=" + this.ethnic + '}';
        } else {
            return this.idCardSide.equals("back") ? "IDCardResult back{, signDate=" + this.signDate + ", expiryDate=" + this.expiryDate + ", issueAuthority=" + this.issueAuthority + '}' : "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(direction);
        parcel.writeInt(wordsResultNumber);
        parcel.writeString(address);
        parcel.writeString(idNumber);
        parcel.writeString(birthday);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(ethnic);
        parcel.writeString(idCardSide);
        parcel.writeString(riskType);
        parcel.writeString(imageStatus);
        parcel.writeString(signDate);
        parcel.writeString(expiryDate);
        parcel.writeString(issueAuthority);
        parcel.writeString(url);
    }

    public static final Creator<IDCardBean> CREATOR=new Creator<IDCardBean>() {
        @Override
        public IDCardBean createFromParcel(Parcel parcel) {
            IDCardBean idCardBean=new IDCardBean();
            idCardBean.direction=parcel.readInt();
            idCardBean.wordsResultNumber=parcel.readInt();
            idCardBean.address=parcel.readString();
            idCardBean.idNumber=parcel.readString();
            idCardBean.birthday=parcel.readString();
            idCardBean.name=parcel.readString();
            idCardBean.gender=parcel.readString();
            idCardBean.ethnic=parcel.readString();
            idCardBean.idCardSide=parcel.readString();
            idCardBean.riskType=parcel.readString();
            idCardBean.imageStatus=parcel.readString();
            idCardBean.signDate=parcel.readString();
            idCardBean.expiryDate=parcel.readString();
            idCardBean.issueAuthority=parcel.readString();
            idCardBean.url=parcel.readString();
            return idCardBean;
        }
        @Override
        public IDCardBean[] newArray(int i) {
            return new IDCardBean[i];
        }
    };
}
