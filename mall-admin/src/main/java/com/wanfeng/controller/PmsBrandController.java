package com.wanfeng.controller;

import com.wanfeng.dto.PmsBrandParam;
import com.wanfeng.entry.R;
import com.wanfeng.entry.PageResult;
import com.wanfeng.pojo.PmsBrand;
import com.wanfeng.service.PmsBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R list(){
        return R.success(pmsBrandService.list());
    }

    @ApiOperation("添加品牌")
    @PostMapping("/create")
    public R create(@Validated @RequestBody PmsBrandParam pmsBrandParam){
        int count = pmsBrandService.create(pmsBrandParam);
        if(count > 0) {
            return R.success(count);
        }else{
            return R.failed();
        }
    }

    @ApiOperation("更新品牌")
    @PostMapping("/update/{id}")
    public R update(@PathVariable Long id, @Validated @RequestBody PmsBrandParam pmsBrandParam){
        int count = pmsBrandService.update(id, pmsBrandParam);
        if(count > 0){
            return R.success(count);
        }else {
            return R.failed();
        }
    }

    @ApiOperation("删除品牌")
    @PostMapping("/del/{id}")
    public R delete(@PathVariable Long id){
        int count = pmsBrandService.delete(id);
        if (count > 0) {
            return R.success(count);
        }else {
            return R.failed();
        }
    }

    @ApiOperation("批量删除")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestParam("ids") List<Long> ids){
        int count = pmsBrandService.deleteBatch(ids);
        if(count > 0){
            return R.success(count);
        }else{
            return R.failed();
        }
    }

    @ApiOperation("批量修改状态")
    @PostMapping("/update/showStatus")
    public R updateShowStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus")Integer showStatus){
        int count = pmsBrandService.updateShowStatus(ids,showStatus);
        if(count > 0){
            return R.success(count);
        }else{
            return R.failed();
        }
    }

    @ApiOperation(value = "批量更新厂家制造商状态")
    @PostMapping("/update/factoryStatus")
    public R updateFactoryStatus(@RequestParam("ids") List<Long> ids, @RequestParam("factoryStatus")Integer factoryStatus){
        int count = pmsBrandService.updateFactoryStatus(ids,factoryStatus);
        if(count > 0){
            return R.success(count);
        }else{
            return R.failed();
        }
    }

    @ApiOperation("根据id获取品牌信息")
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        return R.success(pmsBrandService.getById(id));
    }


    @ApiOperation("分页获取品牌信息")
    @GetMapping("/listByPage")
    public R getList(@RequestParam("keyWord")String keyWord, @RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize){
        PageResult<PmsBrand> pageResult = PageResult.getPageResult(pmsBrandService.pageList(keyWord, pageNum, pageSize));
        return R.success(pageResult);
    }
}
