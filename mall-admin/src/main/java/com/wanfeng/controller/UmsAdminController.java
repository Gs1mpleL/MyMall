package com.wanfeng.controller;

import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.entry.CommonResult;
import com.wanfeng.service.UmsAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanfeng
 * @created 2022/3/28 19:39
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService umsAdminService;



    @ApiOperation("登录并返回token")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody UmsAdminParam umsAdminParam){
        String token = umsAdminService.login(umsAdminParam);
        if(token == null){
            return CommonResult.validateFailed("用户名或密码错误!");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);

    }
}
