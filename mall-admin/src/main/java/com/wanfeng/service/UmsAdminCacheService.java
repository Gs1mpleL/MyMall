package com.wanfeng.service;

import com.wanfeng.pojo.UmsAdmin;

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
     * @return
     */
    UmsAdmin getAdmin(String username);
}
