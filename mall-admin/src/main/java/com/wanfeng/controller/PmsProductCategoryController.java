package com.wanfeng.controller;

import com.wanfeng.dto.PmsProductCategoryParam;
import com.wanfeng.dto.PmsProductCategoryWithChildrenItem;
import com.wanfeng.entry.CommonResult;
import com.wanfeng.entry.PageResult;
import com.wanfeng.pojo.PmsProductCategory;
import com.wanfeng.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanfeng
 * @create 2022/3/27 12:07
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/productCategory")
@Api(tags = "PmsProductCategoryController", value = "商品分类管理")
public class PmsProductCategoryController {
    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;


    @ApiOperation("根据ID获取商品分类")
    @GetMapping("/{id}")
    public CommonResult getProductCategoryById(@PathVariable Long id){
        PmsProductCategory p = pmsProductCategoryService.getProductCategoryById(id);
        return CommonResult.success(p);
    }

    @ApiOperation("添加商品分类")
    @PostMapping("/create")
    public CommonResult create(@RequestBody @Validated PmsProductCategoryParam pmsProductCategoryParam){
        int count = pmsProductCategoryService.create(pmsProductCategoryParam);
        if(count > 0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改商品分类")
    @PostMapping("/update/{id}")
    public CommonResult updateById(@PathVariable Long id,@RequestBody @Validated PmsProductCategoryParam pmsProductCategoryParam) {
        int count = pmsProductCategoryService.updateById(id,pmsProductCategoryParam);
        if(count > 0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }


    @ApiOperation("删除商品分类")
    @PostMapping("/delete/{id}")
    public CommonResult deleteById(@PathVariable Long id){
        int count = pmsProductCategoryService.deleteById(id);
        if(count > 0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改导航栏状态")
    @PostMapping("/update/navStatus")
    public CommonResult updateNavStatus(@RequestParam("ids")List<Long>ids,@RequestParam("navStatus")Integer navStatus){
        int count = pmsProductCategoryService.updateNavStatus(ids,navStatus);
        if(count > 0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改显示状态")
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam("ids")List<Long>ids,@RequestParam("showStatus")Integer showStatus){
        int count = pmsProductCategoryService.updateShowStatus(ids,showStatus);
        if(count > 0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }


    @ApiOperation("分页查询商品分类")
    @GetMapping("/list/{parentId}")
    public CommonResult getList(@PathVariable Long parentId,
                                @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        List<PmsProductCategory> productCategoryList = pmsProductCategoryService.getList(parentId,pageSize,pageNum);
        return CommonResult.success(PageResult.getPageResult(productCategoryList));
    }

    @ApiOperation("查询所有一级分类及子分类")
    @GetMapping("/list/withChildren")
    public CommonResult getListWithChildren(){
        List<PmsProductCategoryWithChildrenItem> list = pmsProductCategoryService.getListWithChildren();
        return CommonResult.success(list);
    }





}
