package com.zeus.modules.trx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CoinsVo {
    private String address;//	账户地址
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;//	账户创建时间，即账户在TRON网络激活时间
    private String balance;//	账户TRX余额
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date latest_opration_time;//	账户最近一次操作的时间
    private String free_net_usage;//	"免费带宽已使用量
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date latest_consume_free_time;//	"账户最近一次消耗免费带宽的时间
    private String net_window_size;//	网络窗口大小

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Date getLatest_opration_time() {
        return latest_opration_time;
    }

    public void setLatest_opration_time(Date latest_opration_time) {
        this.latest_opration_time = latest_opration_time;
    }

    public String getFree_net_usage() {
        return free_net_usage;
    }

    public void setFree_net_usage(String free_net_usage) {
        this.free_net_usage = free_net_usage;
    }

    public Date getLatest_consume_free_time() {
        return latest_consume_free_time;
    }

    public void setLatest_consume_free_time(Date latest_consume_free_time) {
        this.latest_consume_free_time = latest_consume_free_time;
    }

    public String getNet_window_size() {
        return net_window_size;
    }

    public void setNet_window_size(String net_window_size) {
        this.net_window_size = net_window_size;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("\"address\":\"").append(address).append('\"');
        sb.append(", \"create_time\":\"").append(create_time).append('\"');
        sb.append(", \"balance\":\"").append(balance).append('\"');
        sb.append(", \"latest_opration_time\":\"").append(latest_opration_time).append('\"');
        sb.append(", \"free_net_usage\":\"").append(free_net_usage).append('\"');
        sb.append(", \"latest_consume_free_time\":\"").append(latest_consume_free_time).append('\"');
        sb.append(", \"net_window_size\":\"").append(net_window_size).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
