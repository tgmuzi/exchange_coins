package com.zeus.modules.sys.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author muzi
 * @since 2023-04-10
 */
@TableName("SYS_USER_TOKEN")
public class SysUserToken extends Model<SysUserToken> {

    private static final long serialVersionUID = 1L;

    @TableId("USER_ID")
    private String userId;
    @TableField("TOKEN")
    private String token;
    @TableField("EXPIRE_TIME")
    private Date expireTime;
    @TableField("UPDATE_TIME")
    private Date updateTime;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "SysUserToken{" +
        ", userId=" + userId +
        ", token=" + token +
        ", expireTime=" + expireTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
