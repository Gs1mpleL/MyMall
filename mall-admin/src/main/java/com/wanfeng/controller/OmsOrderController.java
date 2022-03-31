package com.wanfeng.controller;

import com.wanfeng.dto.OmsOrderQueryParam;
import com.wanfeng.entry.PageResult;
import com.wanfeng.entry.R;
import com.wanfeng.pojo.OmsOrder;
import com.wanfeng.service.OmsOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/30 11:10
 * @package com.wanfeng.controller
 */
@RestController
@RequestMapping("/order")
public class OmsOrderController {

    @Autowired
    private OmsOrderService omsOrderService;

    @ApiOperation("分页查询订单")
    @GetMapping("/list")
    public R getListByPage(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, @RequestParam(value = "PageSize",defaultValue = "5")Integer pageSize, OmsOrderQueryParam omsOrderQueryParam){
        List<OmsOrder> omsOrders = omsOrderService.getListByPage(omsOrderQueryParam,pageNum,pageSize);
        return R.success(PageResult.getPageResult(omsOrders));
    }

}
