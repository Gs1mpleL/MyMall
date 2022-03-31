package com.wanfeng.controller;

import com.wanfeng.entry.R;
import com.wanfeng.pojo.UmsResource;
import com.wanfeng.service.UmsResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wanfeng
 * @create 2022/3/29 10:33
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/resource")
public class UmsResourceController {
    @Autowired
    private UmsResourceService umsResourceService;

    @ApiOperation("获取所有路径资源")
    @GetMapping("/list")
    public R list(){
        List<UmsResource> list = umsResourceService.list();
        return R.success(list);
    }
}
