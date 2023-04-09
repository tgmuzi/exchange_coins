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
@TableName("TRX_BIG_INTEGER")
public class TrxBigInteger extends Model<TrxBigInteger> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Long id;
    /**
     * 区块高度
     */
    @TableField("BIG_INTEGER")
    private String bigInteger;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(String bigInteger) {
        this.bigInteger = bigInteger;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TrxBigInteger{" +
        ", id=" + id +
        ", bigInteger=" + bigInteger +
        ", createTime=" + createTime +
        "}";
    }
}
