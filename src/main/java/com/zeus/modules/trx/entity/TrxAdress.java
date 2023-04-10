package com.zeus.modules.trx.entity;

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
 * @since 2023-04-09
 */
@TableName("TRX_ADRESS")
public class TrxAdress extends Model<TrxAdress> {

    private static final long serialVersionUID = 1L;

    @TableId("ADRESS_ID")
    private Long adressId;
    /**
     * TRX地址
     */
    @TableField("ADRESS")
    private String adress;
    /**
     * telegram 用户ID
     */
    @TableField("message_Id")
    private String messageId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    public Long getAdressId() {
        return adressId;
    }

    public void setAdressId(Long adressId) {
        this.adressId = adressId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.adressId;
    }

    @Override
    public String toString() {
        return "TrxAdress{" +
        ", adressId=" + adressId +
        ", adress=" + adress +
        ", messageId=" + messageId +
        ", createTime=" + createTime +
        "}";
    }
}
