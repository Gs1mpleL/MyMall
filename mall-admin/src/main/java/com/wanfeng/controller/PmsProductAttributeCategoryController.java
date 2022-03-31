package com.wanfeng.controller;

import com.wanfeng.entry.R;
import com.wanfeng.entry.PageResult;
import com.wanfeng.pojo.PmsProductAttributeCategory;
import com.wanfeng.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.ApiOperation;
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
    public R getById(@PathVariable Long id){
        return R.success(pmsProductAttributeCategoryService.getById(id));
    }

    @ApiOperation("分页获取属性")
    @GetMapping("/list")
    public R listByPage(@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        List<PmsProductAttributeCategory> pmsProductAttributeCategoryList = pmsProductAttributeCategoryService.listByPage(pageNum,pageSize);
        return R.success(PageResult.getPageResult(pmsProductAttributeCategoryList));
    }

    @ApiOperation("删除单个属性分类")
    @PostMapping("/delete/{id}")
    public R deleteById(@PathVariable Long id){
        int count = pmsProductAttributeCategoryService.deleteById(id);
        if(count > 0){
            return R.success(count);
        }else{
            return R.failed();
        }
    }

    @ApiOperation("添加商品属性分类")
    @PostMapping("/create")
    public R create(@RequestParam String name){
        int count = pmsProductAttributeCategoryService.create(name);
        if(count > 0){
            return R.success(count);
        }else{
            return R.failed();
        }
    }
}
