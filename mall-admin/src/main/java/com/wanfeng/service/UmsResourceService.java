package com.wanfeng.service;

import com.wanfeng.pojo.UmsResource;

import java.util.List;

/**
 * @author wanfeng
 * @create 2022/3/29 10:34
 * @package com.wanfeng.service
 */
public interface UmsResourceService {
    List<UmsResource> list();

    /**
     * 获取指定用户的资源
     */
    List<UmsResource> getResourceById(Long adminId);
}
