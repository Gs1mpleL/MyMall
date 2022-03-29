package com.wanfeng.service;

import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.pojo.UmsResource;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/28 23:46
 * @package com.wanfeng.service
 */
public interface UmsAdminCacheService {
    /**
     * 缓存用户信息
     */
    void setAdmin(UmsAdmin umsAdmin);

    /**
     * 获取缓存用户
     */
    UmsAdmin getAdmin(String username);

    /**
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 设置缓存后台用户资源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
