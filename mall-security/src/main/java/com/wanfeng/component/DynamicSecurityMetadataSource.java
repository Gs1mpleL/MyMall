package com.wanfeng.component;

import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 动态权限数据源，用于获取动态权限规则
 * @author wanfeng
 * @create 2022/3/29 10:27
 * @package com.wanfeng.component
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Map<String, ConfigAttribute> configAttributeMap = null;
    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @PostConstruct
    public void loadDataSource() {
        System.out.println("-------------loadDataSource()---------------");
        configAttributeMap = dynamicSecurityService.loadDataSource();
    }

    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        System.out.println("----------- getAttributes()----------------");
        // 确保路径和权限已经一一对应
        if(configAttributeMap == null) this.loadDataSource();
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        // 获取当前访问的路径
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println("获得访问的URL->" + requestUrl);
        String path = URLUtil.getPath(requestUrl);
        System.out.println("获得访问路径->" + path);
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String pattern : configAttributeMap.keySet()) {
            if (pathMatcher.match(pattern, path)) {
                System.out.println("匹配到路径 -> 数据库中:" + pattern + "<--->当前路径 -> " +path);
                configAttributes.add(configAttributeMap.get(pattern));
                System.out.println("当前路径需要权限 : " + configAttributeMap.get(pattern) + "已经填入configAttributes等待权限检查" );
            }
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
