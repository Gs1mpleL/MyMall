package com.wanfeng.component;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 判断用户是否有权限
 * @author wanfeng
 * @create 2022/3/29 10:54
 * @package com.wanfeng.component
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当接口未被配置资源时直接放行
        if (CollUtil.isEmpty(configAttributes)) {
            System.out.println("没有配置资源权限!");
            return;
        }
        // 检查
        for (ConfigAttribute configAttribute : configAttributes) {
            System.out.println("---------------------开始检查权限---------------------");
            String needAuthority = configAttribute.getAttribute();
            System.out.println("需要的权限是" + needAuthority);
            for (GrantedAuthority authority : authentication.getAuthorities()) {

                if (needAuthority.trim().equals(authority.getAuthority())) {
                    System.out.println("需要的权限：" + needAuthority + "\t 用户的权限：" + authority.getAuthority());
                    return;
                }
            }
        }
        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
