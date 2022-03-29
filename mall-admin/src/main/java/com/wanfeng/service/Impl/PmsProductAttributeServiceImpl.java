package com.wanfeng.service.Impl;


import com.wanfeng.pojo.PmsProductAttribute;
import com.wanfeng.service.PmsProductAttributeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/29 17:33
 * @package com.wanfeng.service.Impl
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
    @Override
    public List<PmsProductAttribute> listByCategory(Long cid, Integer type) {
        return null;
    }
}
