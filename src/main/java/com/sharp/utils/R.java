package com.sharp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: R</p>
 * <p>Description: 响应体封装</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author yuanhongwei
 * @version 1.0 2019/7/30 11:35【初版】
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数，初始化成功
     */
    public R() {
        put("code", "00000");
        put("msg", "success");
    }

    /**
     * 用于同时指定code和msg的返回
     * @param code
     * @param msg
     * @return
     */
    public static R common(String code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 返回成功及携带响应返回字段，通常用于正常的结果返回
     * @param map
     * @return
     */
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    /**
     * 返回成功，但是不携带任何参数
     * @return
     */
    public static R ok() {
        return new R();
    }

    /**
     * 参数校验失败返回，包括参数非空、格式校验
     * @param msg
     * @return
     */
    public static R errorParam(String msg) {
        return common("10001", msg);
    }

    /**
     * 消息头校验失败返回
     * @return
     */
    public static R errorHeader() {
        return common("10002", "消息头未按约定设置");
    }

    /**
     * 权限校验失败返回
     * @return
     */
    public static R errorAuth() {
        return common("20001", "你没有访问权限");
    }

    /**
     * 嵌套多层的消息使用，一般情况用不到
     */
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
