package com.wanfeng.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用返回结构
 * @author wanfeng
 * @create 2022/3/27 12:13
 * @package PACKAGE_NAME
 */
@Data
@AllArgsConstructor
public class R {
    /**
     * 状态码
     */
    private long code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private Object data;

    /**
     * 操作成功返回结果
     */
    public static R success(Object o){
        return new R(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),o);
    }
    public static R forbidden(Object data) {
        return new R(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 未登录返回结果
     */
    public static R unauthorized(Object data) {
        return new R(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 操作成功返回结果和信息
     */
    public static R success(Object o, String message){
        return new R(ResultCode.SUCCESS.getCode(),message,o);
    }

    /**
     * 失败返回结果
     */
    public static R failed(){
        return new R(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    /**
     * 失败返回结果
     */
    public static R failed(String message){
        return new R(ResultCode.FAILED.getCode(),message, null);
    }

    /**
     * 参数验证失败返回信息
     */
    public static R validateFailed(){
        return new R(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
    }

    /**
     * 参数验证失败返回信息
     */
    public static R validateFailed(String message){
        return new R(ResultCode.VALIDATE_FAILED.getCode(), message,null);
    }
}
