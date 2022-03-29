package com.wanfeng.service.Impl;

import com.github.pagehelper.PageHelper;
import com.wanfeng.mapper.PmsProductAttributeCategoryMapper;
import com.wanfeng.pojo.PmsProductAttributeCategory;
import com.wanfeng.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/28 15:51
 * @package com.wanfeng.service.Impl
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;



    @Override
    public PmsProductAttributeCategory getById(Long id) {
        return pmsProductAttributeCategoryMapper.selectById(id);
    }

    @Override
    public List<PmsProductAttributeCategory> listByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return pmsProductAttributeCategoryMapper.selectList(null);
    }

    @Override
    public int deleteById(Long id) {
        return pmsProductAttributeCategoryMapper.deleteById(id);
    }

    @Override
    public int create(String name) {
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setName(name);
        return pmsProductAttributeCategoryMapper.insert(pmsProductAttributeCategory);
    }
}
