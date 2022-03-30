package com.wanfeng.service;

import com.wanfeng.dto.PmsProductParam;
import com.wanfeng.dto.PmsProductResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wanfeng
 * @created 2022/3/27 23:38
 * @package com.wanfeng.service
 */
public interface PmsProductService {
    PmsProductResult getById(Long id);

    @Transactional
    int create(PmsProductParam pmsProductParam);
}
