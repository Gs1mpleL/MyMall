package com.wanfeng.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.wanfeng.dto.PmsBrandParam;
import com.wanfeng.mapper.PmsBrandMapper;
import com.wanfeng.pojo.PmsBrand;
import com.wanfeng.service.PmsBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/27 20:30
 * @package com.wanfeng.service.Impl
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired
    private PmsBrandMapper pmsBrandMapper;
    @Override
    public List<PmsBrand> list() {
        return pmsBrandMapper.selectList(null);
    }

    @Override
    public int create(PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam,pmsBrand);
        if(StringUtils.isEmpty(pmsBrand.getFirstLetter())){
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0,1));
        }
        return pmsBrandMapper.insert(pmsBrand);
    }

    @Override
    public int update(Long id, PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setId(id);
        BeanUtils.copyProperties(pmsBrandParam,pmsBrand);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        // 这里还需要添加修改商品的品牌名的操作
        return pmsBrandMapper.updateById(pmsBrand);
    }

    @Override
    public int delete(Long id) {
        return pmsBrandMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return pmsBrandMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        return pmsBrandMapper.updateShowStatus(ids,showStatus);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        return pmsBrandMapper.updateFactoryStatus(ids,factoryStatus);
    }

    @Override
    public PmsBrand getById(Long id) {
        return pmsBrandMapper.selectById(id);
    }

    @Override
    public List<PmsBrand> pageList(String keyWord, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return pmsBrandMapper.selectList(new QueryWrapper<PmsBrand>().like("name",  keyWord));
    }
}
