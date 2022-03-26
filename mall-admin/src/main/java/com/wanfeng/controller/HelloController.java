package com.wanfeng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanfeng
 * @created 2022/3/27 0:57
 * @package com.wanfeng.controller
 */
@RestController
public class HelloController {
    @GetMapping("/test")
    public void test(){
        System.out.println("run!!!");
    }
}
