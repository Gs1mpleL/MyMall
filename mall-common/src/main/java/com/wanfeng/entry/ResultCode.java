package com.wanfeng.entry;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wanfeng
 * @create 2022/3/27 12:17
 * @package PACKAGE_NAME
 */
@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCESS(200,"操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    FORBIDDEN(403, "没有相关权限"),
    UNAUTHORIZED(401, "暂未登录或token已经过期");


    // 状态码
    private long code;
    // 提示信息
    private String message;

}
