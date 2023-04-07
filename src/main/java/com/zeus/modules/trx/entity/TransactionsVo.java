package com.zeus.modules.trx.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TransactionsVo {
    private List<?> ret;
    private JSONObject raw_data;
    private Date blockTimestamp;

    public List<?> getRet() {
        return ret;
    }

    public void setRet(List<?> ret) {
        this.ret = ret;
    }

    public JSONObject getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(JSONObject raw_data) {
        this.raw_data = raw_data;
    }

    public Date getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(Date blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TransactionsVo{");
        sb.append("ret=").append(ret);
        sb.append(", raw_data=").append(raw_data);
        sb.append(", blockTimestamp=").append(blockTimestamp);
        sb.append('}');
        return sb.toString();
    }
}
