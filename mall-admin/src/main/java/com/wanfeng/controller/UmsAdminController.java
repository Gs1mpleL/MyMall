package com.wanfeng.controller;

import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.entry.R;
import com.wanfeng.entry.PageResult;
import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.service.UmsAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
    public R login(@Validated @RequestBody UmsAdminParam umsAdminParam){
        String token = umsAdminService.login(umsAdminParam);
        if(token == null){
            return R.validateFailed("用户名或密码错误!");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return R.success(tokenMap);
    }

    @ApiOperation("用户注册")
    @PostMapping("register")
    public R register(@Validated @RequestBody UmsAdminParam umsAdminParam){
        UmsAdmin umsAdmin =  umsAdminService.register(umsAdminParam);
        if(umsAdmin!=null){
            return R.success(umsAdmin);
        }else{
            return R.failed();
        }
    }


    @ApiOperation("刷新token")
    @PostMapping("/refreshToken")
    public R refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String refreshToken = umsAdminService.refreshToken(token);
        // 把新的token返回即可
        return R.success(refreshToken);
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public R getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("这里写了从ThreadLocal中获取用户信息 就不继续写了");
        return R.success(authentication);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public R logout(){
        return R.success(null);
    }

    @ApiOperation("根据用户名分页获取用户列表")
    @GetMapping("/list")
    public R list(@RequestParam(value = "keyword",required = false) String keyword,
                  @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        List<UmsAdmin> list = umsAdminService.list(keyword,pageNum,pageSize);
        return R.success(PageResult.getPageResult(list));
    }

    @ApiOperation("修改指定用户的信息")
    @PostMapping("/update/{id}")
    public R updateById(@PathVariable("id") Long id, @RequestBody UmsAdmin umsAdmin){
        int count = umsAdminService.updateById(id,umsAdmin);
        if (count > 0) {
            return R.success(count);
        }else{
            return R.failed();
        }
    }


    @ApiOperation("删除账号")
    @PostMapping("/del/{id}")
    public R delById(@PathVariable Long id){
        int count = umsAdminService.delById(id);
        if (count > 0) {
            return R.success(count);
        }else{
            return R.failed();
        }
    }

    @ApiOperation("获取用户的角色")
    @PostMapping("/info/role/{id}")
    public R getRoleInfoById(@PathVariable Long id){
        return R.success(umsAdminService.getRoleInfoById(id));
    }

}
