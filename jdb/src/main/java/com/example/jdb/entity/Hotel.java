package com.example.jdb.entity;


/**
 * 酒店实体类
 *
 * @author 白帅雷
 * @date 2019-01-15
 */
public class Hotel {

    /**
     * 酒店ID
     */
    private String hiId;

    /**
     * 酒店编码
     */
    private String hiCode;

    /**
     * 集团ID
     */
    private String blocId;

    /**
     * 集团名称
     */
    private String blocName;

    /**
     * 酒店名称
     */
    private String hiName;

    /**
     * 酒店工商法人名称
     */
    private String hiLegalPerson;

    /**
     * 服务电话
     */
    private String hiTel;

    /**
     * 酒店类型
     */
    private String hiType;

    /**
     * 酒店地址
     */
    private String hiAddress;

    /**
     * 县级行政区划
     */
    private String hiDistrictsCode;

    /**
     * 经度
     */
    private String hiLongitude;

    /**
     * 维度
     */
    private String hiLatitude;

    /**
     * 是否是示范酒店
     */
    private String hiIfDemoHotel;

    /**
     * 删除标识
     */
    private Integer delFlag;

    public String getHiId() {
        return hiId;
    }

    public void setHiId(String hiId) {
        this.hiId = hiId;
    }

    public String getHiCode() {
        return hiCode;
    }

    public void setHiCode(String hiCode) {
        this.hiCode = hiCode;
    }

    public String getBlocId() {
        return blocId;
    }

    public void setBlocId(String blocId) {
        this.blocId = blocId;
    }

    public String getBlocName() {
        return blocName;
    }

    public void setBlocName(String blocName) {
        this.blocName = blocName;
    }

    public String getHiName() {
        return hiName;
    }

    public void setHiName(String hiName) {
        this.hiName = hiName;
    }

    public String getHiLegalPerson() {
        return hiLegalPerson;
    }

    public void setHiLegalPerson(String hiLegalPerson) {
        this.hiLegalPerson = hiLegalPerson;
    }

    public String getHiTel() {
        return hiTel;
    }

    public void setHiTel(String hiTel) {
        this.hiTel = hiTel;
    }

    public String getHiType() {
        return hiType;
    }

    public void setHiType(String hiType) {
        this.hiType = hiType;
    }

    public String getHiAddress() {
        return hiAddress;
    }

    public void setHiAddress(String hiAddress) {
        this.hiAddress = hiAddress;
    }

    public String getHiDistrictsCode() {
        return hiDistrictsCode;
    }

    public void setHiDistrictsCode(String hiDistrictsCode) {
        this.hiDistrictsCode = hiDistrictsCode;
    }

    public String getHiLongitude() {
        return hiLongitude;
    }

    public void setHiLongitude(String hiLongitude) {
        this.hiLongitude = hiLongitude;
    }

    public String getHiLatitude() {
        return hiLatitude;
    }

    public void setHiLatitude(String hiLatitude) {
        this.hiLatitude = hiLatitude;
    }

    public String getHiIfDemoHotel() {
        return hiIfDemoHotel;
    }

    public void setHiIfDemoHotel(String hiIfDemoHotel) {
        this.hiIfDemoHotel = hiIfDemoHotel;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hiId='" + hiId + '\'' +
                ", hiCode='" + hiCode + '\'' +
                ", blocId='" + blocId + '\'' +
                ", blocName='" + blocName + '\'' +
                ", hiName='" + hiName + '\'' +
                ", hiLegalPerson='" + hiLegalPerson + '\'' +
                ", hiTel='" + hiTel + '\'' +
                ", hiType='" + hiType + '\'' +
                ", hiAddress='" + hiAddress + '\'' +
                ", hiDistrictsCode='" + hiDistrictsCode + '\'' +
                ", hiLongitude='" + hiLongitude + '\'' +
                ", hiLatitude='" + hiLatitude + '\'' +
                ", hiIfDemoHotel='" + hiIfDemoHotel + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
