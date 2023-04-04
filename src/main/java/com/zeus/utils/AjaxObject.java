package com.zeus.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class AjaxObject extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public AjaxObject() {
        put("code", 0);
    }

    public static AjaxObject error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Third party:bad service");
    }

    public static AjaxObject error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static AjaxObject error(int code, String msg) {
        AjaxObject r = new AjaxObject();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static AjaxObject ok(String msg) {
        AjaxObject r = new AjaxObject();
        r.put("msg", msg);
        return r;
    }

    public static AjaxObject ok(Map<String, Object> map) {
        AjaxObject r = new AjaxObject();
        r.putAll(map);
        return r;
    }

    public static AjaxObject ok() {
        return new AjaxObject().put("msg", "");// 添加msg空节点，保证节点一致，update for xiaobin at 20180607
    }

    public AjaxObject put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public AjaxObject data(Object value) {
        super.put("data", value);
        return this;
    }

    public static AjaxObject apiError(String msg) {
        return error(1, msg);
    }
}
