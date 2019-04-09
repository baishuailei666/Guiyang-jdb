package com.example.jdb.entity;


import java.math.BigDecimal;

/**
 * 设备运行占用系统资源状况历史实体类
 *
 * @author 白帅雷
 * @date 2019-01-09
 */
public class MonitoringHistory {

    /**
     * 时间
     */
    private String monitorTime;

    /**
     * 系统CPU总体使用情况
     */
    private BigDecimal sysCpu;

    /**
     * 酒店帮程序占用CPU情况
     */
    private BigDecimal useCpu;

    /**
     * 系统内存总体使用情况
     */
    private BigDecimal sysMem;

    /**
     * 酒店帮程序占用内存情况
     */
    private BigDecimal useMem;

    /**
     * 系统磁盘总体使用情况
     */
    private BigDecimal sysDisk;

    /**
     * 酒店帮程序占用磁盘情况
     */
    private BigDecimal useDisk;

    /**
     * 终端ID
     */
    private String tiId;

    /**
     * 酒店ID
     */
    private String hiId;

    /**
     * 终端软件ID
     */
    private String tiSwId;

    /**
     * 终端编号
     */
    private String tiCode;

    /**
     * 批次编号
     */
    private String bhId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 终端类型
     */
    private String tiType;

    /**
     * 终端上架时间
     */
    private String tiUpTime;

    /**
     * 终端下架时间
     */
    private String tiDownTime;

    /**
     * 维护人员ID
     */
    private String tiUserId;

    /**
     * 维护人员名称
     */
    private String tiUserName;


    /**
     * 设备型号ID
     */
    private String mId;

    /**
     * 设备型号
     */
    private String emMode;

    /**
     * 设备状态
     */
    private String emStatus;

    /**
     * 创建人ID
     */
    private String cUserId;

    /**
     * 创建人名称
     */
    private String cUserName;

    /**
     * 创建时间
     */
    private String cTime;

    /**
     * 修改人ID
     */
    private String mUserId;

    /**
     * 修改人名称
     */
    private String mUserName;

    /**
     * 修改时间
     */
    private String mTime;

    /**
     * 删除标识
     */
    private Integer delFlag;

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }

    public BigDecimal getSysCpu() {
        return sysCpu;
    }

    public void setSysCpu(BigDecimal sysCpu) {
        this.sysCpu = sysCpu;
    }

    public BigDecimal getUseCpu() {
        return useCpu;
    }

    public void setUseCpu(BigDecimal useCpu) {
        this.useCpu = useCpu;
    }

    public BigDecimal getSysMem() {
        return sysMem;
    }

    public void setSysMem(BigDecimal sysMem) {
        this.sysMem = sysMem;
    }

    public BigDecimal getUseMem() {
        return useMem;
    }

    public void setUseMem(BigDecimal useMem) {
        this.useMem = useMem;
    }

    public BigDecimal getSysDisk() {
        return sysDisk;
    }

    public void setSysDisk(BigDecimal sysDisk) {
        this.sysDisk = sysDisk;
    }

    public BigDecimal getUseDisk() {
        return useDisk;
    }

    public void setUseDisk(BigDecimal useDisk) {
        this.useDisk = useDisk;
    }

    public String getTiId() {
        return tiId;
    }

    public void setTiId(String tiId) {
        this.tiId = tiId;
    }

    public String getcUserId() {
        return cUserId;
    }

    public void setcUserId(String cUserId) {
        this.cUserId = cUserId;
    }

    public String getcUserName() {
        return cUserName;
    }

    public void setcUserName(String cUserName) {
        this.cUserName = cUserName;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getHiId() {
        return hiId;
    }

    public void setHiId(String hiId) {
        this.hiId = hiId;
    }

    public String getTiSwId() {
        return tiSwId;
    }

    public void setTiSwId(String tiSwId) {
        this.tiSwId = tiSwId;
    }

    public String getTiCode() {
        return tiCode;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setTiCode(String tiCode) {
        this.tiCode = tiCode;
    }

    public String getBhId() {
        return bhId;
    }

    public void setBhId(String bhId) {
        this.bhId = bhId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getTiType() {
        return tiType;
    }

    public void setTiType(String tiType) {
        this.tiType = tiType;
    }

    public String getTiUpTime() {
        return tiUpTime;
    }

    public void setTiUpTime(String tiUpTime) {
        this.tiUpTime = tiUpTime;
    }

    public String getTiDownTime() {
        return tiDownTime;
    }

    public void setTiDownTime(String tiDownTime) {
        this.tiDownTime = tiDownTime;
    }

    public String getTiUserId() {
        return tiUserId;
    }

    public void setTiUserId(String tiUserId) {
        this.tiUserId = tiUserId;
    }

    public String getTiUserName() {
        return tiUserName;
    }

    public void setTiUserName(String tiUserName) {
        this.tiUserName = tiUserName;
    }

    public String getEmMode() {
        return emMode;
    }

    public void setEmMode(String emMode) {
        this.emMode = emMode;
    }

    public String getEmStatus() {
        return emStatus;
    }

    public void setEmStatus(String emStatus) {
        this.emStatus = emStatus;
    }

    @Override
    public String toString() {
        return "MonitoringHistory{" +
                "monitorTime='" + monitorTime + '\'' +
                ", sysCpu=" + sysCpu +
                ", useCpu=" + useCpu +
                ", sysMem=" + sysMem +
                ", useMem=" + useMem +
                ", sysDisk=" + sysDisk +
                ", useDisk=" + useDisk +
                ", tiId='" + tiId +
                '}';
    }
}
