package com.wanfeng.service;

import com.wanfeng.dto.PmsProductCategoryParam;
import com.wanfeng.dto.PmsProductCategoryWithChildrenItem;
import com.wanfeng.pojo.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wanfeng
 * @create 2022/3/27 13:30
 * @package com.wanfeng.service.Impl
 */
public interface PmsProductCategoryService {

    PmsProductCategory getProductCategoryById(Long id);
    @Transactional
    int create(PmsProductCategoryParam pmsProductCategoryParam);

    List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);
    @Transactional
    int updateById(Long id, PmsProductCategoryParam pmsProductCategoryParam);

    int deleteById(Long id);

    int updateNavStatus(List<Long> ids, Integer navStatus);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    List<PmsProductCategoryWithChildrenItem> getListWithChildren();
}
