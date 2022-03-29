package com.wanfeng.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 提供获取权限的服务
 * @author wanfeng
 * @create 2022/3/29 10:28
 * @package com.wanfeng.component
 */
public interface DynamicSecurityService {
    Map<String, ConfigAttribute> loadDataSource();
}
