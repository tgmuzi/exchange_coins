package com.zeus.modules.trx.utils;

public enum ErrorCode {

    ACCOUNT_ERROR(1, "账户异常"),
            ;
    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
