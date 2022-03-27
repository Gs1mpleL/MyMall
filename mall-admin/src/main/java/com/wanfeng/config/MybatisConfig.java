package com.wanfeng.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanfeng
 * @create 2022/3/27 15:31
 * @package com.wanfeng.config
 */
@Configuration
public class MybatisConfig {
    @Bean
    public PageInterceptor pageInterceptor(){
        return new PageInterceptor();
    }
}
