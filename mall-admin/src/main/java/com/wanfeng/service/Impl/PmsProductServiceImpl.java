package com.wanfeng.service.Impl;

import com.wanfeng.dto.PmsProductResult;
import com.wanfeng.mapper.PmsProductMapper;
import com.wanfeng.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanfeng
 * @created 2022/3/27 23:38
 * @package com.wanfeng.service.Impl
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {

    @Autowired
    private PmsProductMapper pmsProductMapper;


    /**
     * 还有问题 先写别的模块了
     * @param id
     * @return
     */
    @Override
    public PmsProductResult getById(Long id) {
        PmsProductResult pmsProductResult = pmsProductMapper.getById(id);
        return null;
    }
}
