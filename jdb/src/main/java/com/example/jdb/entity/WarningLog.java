package com.example.jdb.entity;


import org.springframework.beans.factory.annotation.Value;

/**
 * 异常日志实体类
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
public class WarningLog {

    /**
     * 主键ID
     */
    private Integer pkId;
    /**
     * 终端ID
     */
    private String tiId;

    /**
     * 监控时间
     */
    private String monitorTime;

    /**
     * 日志压缩文件存储路径
     */
    private String logPath;

    /**
     * 日志是否处理
     */
    private Integer logIsDispose;

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

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getTiId() {
        return tiId;
    }

    public void setTiId(String tiId) {
        this.tiId = tiId;
    }

    public String getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(String monitorTime) {
        this.monitorTime = monitorTime;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public Integer getLogIsDispose() {
        return logIsDispose;
    }

    public void setLogIsDispose(Integer logIsDispose) {
        this.logIsDispose = logIsDispose;
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

    @Override
    public String toString() {
        return "WarningLog{" +
                "pkId='" + pkId + '\'' +
                "tiId='" + tiId + '\'' +
                ", monitorTime='" + monitorTime + '\'' +
                ", logPath='" + logPath + '\'' +
                ", logIsDispose='" + logIsDispose + '\'' +
                ", cUserId='" + cUserId + '\'' +
                ", cUserName='" + cUserName + '\'' +
                ", cTime='" + cTime + '\'' +
                ", mUserId='" + mUserId + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mTime='" + mTime + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
