package com.wanfeng.controller;

import com.wanfeng.dto.PmsBrandParam;
import com.wanfeng.entry.CommonResult;
import com.wanfeng.service.PmsBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanfeng
 * @created 2022/3/27 20:29
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService pmsBrandService;

    @ApiOperation("获取全部品牌列表")
    @GetMapping("/list")
    public CommonResult list(){
        return CommonResult.success(pmsBrandService.list());
    }

    @ApiOperation("添加品牌")
    @PostMapping("/create")
    public CommonResult create(@Validated @RequestBody PmsBrandParam pmsBrandParam){
        int count = pmsBrandService.create(pmsBrandParam);
        if(count > 0) {
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新品牌")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id,@Validated @RequestBody PmsBrandParam pmsBrandParam){
        int count = pmsBrandService.update(id, pmsBrandParam);
        if(count > 0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }
}
