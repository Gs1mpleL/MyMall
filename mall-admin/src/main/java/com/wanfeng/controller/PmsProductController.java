package com.wanfeng.controller;

import com.wanfeng.dto.PmsProductResult;
import com.wanfeng.entry.CommonResult;
import com.wanfeng.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanfeng
 * @created 2022/3/27 23:37
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService pmsProductService;

    @ApiOperation("根据id获得商品信息")
    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable Long id){
         PmsProductResult pmsProductResult  = pmsProductService.getById(id);
         return CommonResult.success(pmsProductResult);
    }

}
