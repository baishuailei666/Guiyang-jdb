package com.example.jdb.entity;


/**
 * 终端软件信息实体类
 *
 * @author 白帅雷
 * @date 2019-01-14
 */
public class SoftwareInfo {

    /**
     * 终端软件ID
     */
    private String tiSwId;

    /**
     * 酒店ID
     */
    private String hiId;

    /**
     * 软件名称
     */
    private String tiSwName;

    /**
     * 软件路径
     */
    private String tiSwPath;

    /**
     * 软件版本号
     */
    private String tiSwVersion;

    public String getTiSwId() {
        return tiSwId;
    }

    public void setTiSwId(String tiSwId) {
        this.tiSwId = tiSwId;
    }

    public String getHiId() {
        return hiId;
    }

    public void setHiId(String hiId) {
        this.hiId = hiId;
    }

    public String getTiSwName() {
        return tiSwName;
    }

    public void setTiSwName(String tiSwName) {
        this.tiSwName = tiSwName;
    }

    public String getTiSwPath() {
        return tiSwPath;
    }

    public void setTiSwPath(String tiSwPath) {
        this.tiSwPath = tiSwPath;
    }

    public String getTiSwVersion() {
        return tiSwVersion;
    }

    public void setTiSwVersion(String tiSwVersion) {
        this.tiSwVersion = tiSwVersion;
    }

    @Override
    public String toString() {
        return "SoftwareInfo{" +
                "tiSwId='" + tiSwId + '\'' +
                ", hiId='" + hiId + '\'' +
                ", tiSwName='" + tiSwName + '\'' +
                ", tiSwPath='" + tiSwPath + '\'' +
                ", tiSwVersion='" + tiSwVersion + '\'' +
                '}';
    }
}
