package com.wanfeng.config;

import com.wanfeng.component.DynamicSecurityService;
import com.wanfeng.pojo.UmsResource;
import com.wanfeng.service.UmsAdminService;
import com.wanfeng.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanfeng
 * @created 2022/3/28 18:10
 * @package com.wanfeng.config
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig{


    private UmsAdminService  umsAdminService;
    private UmsResourceService umsResourceService;

    @Autowired
    public AdminSecurityConfig(UmsAdminService umsAdminService, UmsResourceService umsResourceService) {
        this.umsAdminService = umsAdminService;
        this.umsResourceService = umsResourceService;
    }

    /**
     * 自定义登录Service处理器
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username-> umsAdminService.loginByUsername(username);
    }


    /**
     * 动态获取权限服务的实现
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return new DynamicSecurityService() {
            // 实现加载资源的方法
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                System.out.println("开始加载资源信息");
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                // 从数据库中查询操作和权限对应的路径
                List<UmsResource> resourceList = umsResourceService.list();
                for (UmsResource umsResource : resourceList) {
                    map.put(umsResource.getUrl(),new org.springframework.security.access.SecurityConfig(umsResource.getId()+ ":" + umsResource.getName()));
                    System.out.println("路径:" + umsResource.getUrl() + "\t对应配置 -> " +umsResource.getId()+ ":" + umsResource.getName());
                }

                System.out.println("------------------路径和权限已经一一对应---------------------");
                return map;
            }
        };
    }
}
