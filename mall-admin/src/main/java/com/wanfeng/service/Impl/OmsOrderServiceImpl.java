package com.wanfeng.service.Impl;

import com.github.pagehelper.PageHelper;
import com.wanfeng.dto.OmsOrderQueryParam;
import com.wanfeng.mapper.OmsOrderMapper;
import com.wanfeng.pojo.OmsOrder;
import com.wanfeng.service.OmsOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/31 12:55
 * @package com.wanfeng.service.Impl
 */
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    private OmsOrderMapper omsOrderMapper;


    @Override
    public List<OmsOrder> getListByPage(OmsOrderQueryParam omsOrderQueryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return omsOrderMapper.selectByParam(omsOrderQueryParam);
    }
}
