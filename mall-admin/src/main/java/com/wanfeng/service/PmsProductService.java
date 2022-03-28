package com.wanfeng.service;

import com.wanfeng.dto.PmsProductResult;

/**
 * @author wanfeng
 * @created 2022/3/27 23:38
 * @package com.wanfeng.service
 */
public interface PmsProductService {
    PmsProductResult getById(Long id);
}
