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
public class CommonResult {
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
    public static CommonResult success(Object o){
        return new CommonResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),o);
    }

    /**
     * 操作成功返回结果和信息
     */
    public static CommonResult success(Object o,String message){
        return new CommonResult(ResultCode.SUCCESS.getCode(),message,o);
    }

    /**
     * 失败返回结果
     */
    public static CommonResult failed(){
        return new CommonResult(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    /**
     * 失败返回结果
     */
    public static CommonResult failed(String message){
        return new CommonResult(ResultCode.FAILED.getCode(),message, null);
    }

    /**
     * 参数验证失败返回信息
     */
    public static CommonResult validateFailed(){
        return new CommonResult(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
    }

    /**
     * 参数验证失败返回信息
     */
    public static CommonResult validateFailed(String message){
        return new CommonResult(ResultCode.VALIDATE_FAILED.getCode(), message,null);
    }
}
