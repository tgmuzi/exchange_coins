package com.zeus.modules.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author muzi
 * @since 2022-08-19
 */
@TableName("SYS_USER")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId("USER_ID")
    private Long userId;
    @TableField("USER_NAME")
    private String userName;
    @TableField("PASSWORD")
    private String password;
    @TableField("GA_SECRET")
    private String gaSecret;
    @TableField("GA_SECRET_IMG")
    private String gaSecretImg;
    @TableField("CREATE_TIME")
    private Date createTime;
    @TableField("CREATE_USER_ID")
    private Long createUserId;
    @TableField("STATUS")
    private Integer status;
    @TableField("SALT")
    private String salt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGaSecret() {
        return gaSecret;
    }

    public void setGaSecret(String gaSecret) {
        this.gaSecret = gaSecret;
    }

    public String getGaSecretImg() {
        return gaSecretImg;
    }

    public void setGaSecretImg(String gaSecretImg) {
        this.gaSecretImg = gaSecretImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                ", userId=" + userId +
                ", userName=" + userName +
                ", password=" + password +
                ", gaSecret=" + gaSecret +
                ", gaSecretImg=" + gaSecretImg +
                ", createTime=" + createTime +
                ", createUserId=" + createUserId +
                ", status=" + status +
                ", salt=" + salt +
                "}";
    }
}
