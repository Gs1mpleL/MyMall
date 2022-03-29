package com.wanfeng.controller;

import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.entry.CommonResult;
import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation("用户注册")
    @PostMapping("register")
    public CommonResult register(@Validated @RequestBody UmsAdminParam umsAdminParam){
        UmsAdmin umsAdmin =  umsAdminService.register(umsAdminParam);
        if(umsAdmin!=null){
            return CommonResult.success(umsAdmin);
        }else{
            return CommonResult.failed();
        }
    }


    @ApiOperation("刷新token")
    @PostMapping("/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String refreshToken = umsAdminService.refreshToken(token);
        // 把新的token返回即可
        return CommonResult.success(refreshToken);
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public CommonResult getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("这里写了从ThreadLocal中获取用户信息 就不继续写了");
        return CommonResult.success(authentication);
    }
}
