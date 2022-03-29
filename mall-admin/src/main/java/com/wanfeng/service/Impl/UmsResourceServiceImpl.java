package com.wanfeng.service.Impl;

import cn.hutool.core.collection.CollUtil;
import com.wanfeng.mapper.AdminRoleResourceMapper;
import com.wanfeng.mapper.UmsResourceMapper;
import com.wanfeng.pojo.UmsResource;
import com.wanfeng.service.UmsAdminCacheService;
import com.wanfeng.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanfeng
 * @create 2022/3/29 10:34
 * @package com.wanfeng.service.Impl
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceMapper umsResourceMapper;
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;
    @Autowired
    private AdminRoleResourceMapper adminRoleResourceMapper;

    @Override
    public List<UmsResource> list() {
        return umsResourceMapper.selectList(null);
    }

    @Override
    public List<UmsResource> getResourceById(Long adminId) {
        List<UmsResource> resourceList = umsAdminCacheService.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            System.out.println("从缓存中获取资源成功");
            return resourceList;
        }
        // 查询admin对应的资源
        List<UmsResource> resourceListByAdminId = adminRoleResourceMapper.getResourceListByAdminId(adminId);
        if(CollUtil.isNotEmpty(resourceListByAdminId)){
            umsAdminCacheService.setResourceList(adminId,resourceListByAdminId);
            System.out.println("从数据库中查询到用户资源，并将资源缓存");
        }
        return resourceListByAdminId;
    }
}
