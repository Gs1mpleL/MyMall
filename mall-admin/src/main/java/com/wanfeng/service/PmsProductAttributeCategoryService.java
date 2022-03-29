package com.wanfeng.service;

import com.wanfeng.pojo.PmsProductAttributeCategory;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/28 15:51
 * @package com.wanfeng.service
 */
public interface PmsProductAttributeCategoryService {
    Object getById(Long id);

    List<PmsProductAttributeCategory> listByPage(Integer pageNum, Integer pageSize);

    int deleteById(Long id);

    int create(String name);
}
