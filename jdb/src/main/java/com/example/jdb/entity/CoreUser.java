package com.example.jdb.entity;

/**
 * 运维人员实体类
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
public class CoreUser {
    /**
     * id
     */
    private Integer id;

    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;

    /**
     * password
     */
    private String password;

    /**
     * createTime
     */
    private String createTime;

    /**
     * orgId
     */
    private Integer orgId;

    /**
     * state
     */
    private String state;

    /**
     * jobType1
     */
    private String jobType1;

    /**
     * delFlag
     */
    private Integer delFlag;

    /**
     * updateTime
     */
    private String updateTime;

    /**
     * jobType0
     */
    private String jobType0;

    /**
     * attachmentId
     */
    private String attachmentId;

    /**
     * email
     */
    private String email;

    /**
     * phone
     */
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJobType1() {
        return jobType1;
    }

    public void setJobType1(String jobType1) {
        this.jobType1 = jobType1;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobType0() {
        return jobType0;
    }

    public void setJobType0(String jobType0) {
        this.jobType0 = jobType0;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CoreUser{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createTime='" + createTime + '\'' +
                ", orgId='" + orgId + '\'' +
                ", state='" + state + '\'' +
                ", jobType1='" + jobType1 + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", jobType0='" + jobType0 + '\'' +
                ", attachmentId='" + attachmentId + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
