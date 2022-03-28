package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.dto.PmsProductResult;
import com.wanfeng.pojo.PmsProduct;

/**
 * @author wanfeng
 * @created 2022/3/27 23:38
 * @package com.wanfeng.mapper
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {
    PmsProductResult getById(Long id);
}
