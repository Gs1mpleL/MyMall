package com.wanfeng.controller;

import com.wanfeng.entry.CommonResult;
import com.wanfeng.pojo.PmsProductAttribute;
import com.wanfeng.service.PmsProductAttributeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性
 * @author wanfeng
 * @created 2022/3/29 17:32
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/attribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;


    @ApiOperation("根据商品的分类查询属性列表")
    @GetMapping("/listByCategory/{cid}")
    public CommonResult listByCategory(@PathVariable Long cid,@RequestParam(value = "type") Integer type){
        List<PmsProductAttribute> productAttributeList = pmsProductAttributeService.listByCategory(cid, type);
        return null;
    }
}
