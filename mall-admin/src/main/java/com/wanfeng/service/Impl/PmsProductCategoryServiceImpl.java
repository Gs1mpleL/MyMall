package com.wanfeng.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.wanfeng.dto.PmsProductCategoryParam;
import com.wanfeng.dto.PmsProductCategoryWithChildrenItem;
import com.wanfeng.mapper.PmsProductCategoryMapper;
import com.wanfeng.pojo.PmsProductCategory;
import com.wanfeng.service.PmsProductCategoryService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanfeng
 * @create 2022/3/27 13:32
 * @package com.wanfeng.service.Impl
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {
    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Override
    public PmsProductCategory getProductCategoryById(Long id) {
        return pmsProductCategoryMapper.selectById(id);
    }

    @Override
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        // 设置产品数量为0
        pmsProductCategory.setProductCount(0);
        // 复制属性到新创建的对象
        BeanUtils.copyProperties(pmsProductCategoryParam,pmsProductCategory);
        // 设置商品分类层级
        setCategoryLevel(pmsProductCategory);
        // 商品属性还未添加！
        // 添加商品
        return pmsProductCategoryMapper.insert(pmsProductCategory);
    }

    @Override
    public List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return pmsProductCategoryMapper.selectList(new QueryWrapper<PmsProductCategory>().eq("parent_id",parentId));
    }

    @Override
    public int updateById(Long id, PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setId(id);
        BeanUtils.copyProperties(pmsProductCategoryParam,pmsProductCategory);
        setCategoryLevel(pmsProductCategory);
        // 这里还应该写更改商品名称和修改搜索属性  ！！！
        return pmsProductCategoryMapper.updateById(pmsProductCategory);
    }

    @Override
    public int deleteById(Long id) {
        return pmsProductCategoryMapper.deleteById(id);
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        return pmsProductCategoryMapper.updateNavStatus(ids,navStatus);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        return pmsProductCategoryMapper.updateShowStatus(ids,showStatus);
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> getListWithChildren() {
        return pmsProductCategoryMapper.getListWithChildren();
    }

    private void setCategoryLevel(PmsProductCategory pmsProductCategory){
        // 没有父id时 ，分级为0
        if(pmsProductCategory.getParentId() == 0){
            pmsProductCategory.setLevel(0);
        }else{
            // 先查询负分级的level
            int parentLevel = pmsProductCategoryMapper.selectLevelById(pmsProductCategory.getParentId());
            // 设置子level
            pmsProductCategory.setLevel(parentLevel + 1);
        }
    }
}
