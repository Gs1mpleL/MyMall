package com.wanfeng.service;

import com.wanfeng.dto.PmsBrandParam;

import com.wanfeng.pojo.PmsBrand;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/27 20:29
 * @package com.wanfeng.service
 */
public interface PmsBrandService {
    List<PmsBrand> list();

    int create(PmsBrandParam pmsBrandParam);

    int update(Long id, PmsBrandParam pmsBrandParam);

    int delete(Long id);

    int deleteBatch(List<Long> ids);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);

    PmsBrand getById(Long id);

    List<PmsBrand> pageList(String keyWord, Integer pageNum, Integer pageSize);
}
