package com.wanfeng.exception;

import com.wanfeng.entry.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author wanfeng
 * @created 2022/3/29 16:40
 * @package com.wanfeng.exception
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult handleAllException(Exception e){
        e.printStackTrace();
        return CommonResult.failed(e.getMessage() + "这是我自定义的异常处理结果!!!");
    }
}
