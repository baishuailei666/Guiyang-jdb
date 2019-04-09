package com.example.jdb.entity;

/**
 * 集团实体类
 *
 * @author 白帅雷
 * @date 2019-01-14
 */
public class Bloc {
    /**
     * 集团ID
     */
    private String blocId;
    /**
     * 集团名称
     */
    private String blocName;

    /**
     * 删除标识
     */
    private Integer delFlag;

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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                "blocId='" + blocId + '\'' +
                ", blocName='" + blocName + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
