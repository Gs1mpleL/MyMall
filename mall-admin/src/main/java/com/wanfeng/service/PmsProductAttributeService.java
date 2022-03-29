package com.wanfeng.service;

import com.wanfeng.pojo.PmsProductAttribute;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/29 17:33
 * @package com.wanfeng.service
 */
public interface PmsProductAttributeService {
    List<PmsProductAttribute> listByCategory(Long cid, Integer type);
}
