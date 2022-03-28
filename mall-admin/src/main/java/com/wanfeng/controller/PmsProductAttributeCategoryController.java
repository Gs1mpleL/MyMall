package com.wanfeng.controller;

import com.wanfeng.entry.CommonResult;
import com.wanfeng.entry.PageResult;
import com.wanfeng.pojo.PmsProductAttributeCategory;
import com.wanfeng.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性分类
 * @author wanfeng
 * @created 2022/3/28 15:50
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @ApiOperation("根据Id获取商品属性分类信息")
    @GetMapping("/{id}")
    public CommonResult getById(@PathVariable Long id){
        return CommonResult.success(pmsProductAttributeCategoryService.getById(id));
    }

    @ApiOperation("分页获取属性")
    @GetMapping("/list")
    public CommonResult listByPage(@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        List<PmsProductAttributeCategory> pmsProductAttributeCategoryList = pmsProductAttributeCategoryService.listByPage(pageNum,pageSize);
        return CommonResult.success(PageResult.getPageResult(pmsProductAttributeCategoryList));
    }

    @ApiOperation("删除单个属性分类")
    @PostMapping("/delete/{id}")
    public CommonResult deleteById(@PathVariable Long id){
        int count = pmsProductAttributeCategoryService.deleteById(id);
        if(count > 0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
}
