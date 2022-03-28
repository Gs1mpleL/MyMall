package com.wanfeng.config;

import com.wanfeng.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author wanfeng
 * @created 2022/3/28 18:10
 * @package com.wanfeng.config
 */
@Configuration
@EnableWebSecurity
public class AdminSecurityConfig extends SecurityConfig{

    @Autowired
    private UmsAdminService  umsAdminService;

    /**
     * 自定义登录Service处理器
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username-> umsAdminService.loginByUsername(username);
    }
}
