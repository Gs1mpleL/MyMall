package com.wanfeng.config;

import com.wanfeng.component.JwtAuthenticationTokenFilter;
import com.wanfeng.component.MyAccessDeniedHandler;
import com.wanfeng.component.MytAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author wanfeng
 * @created 2022/3/28 16:58
 * @package com.wanfeng.config
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 获得白名单
     */
    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig(){
        return new IgnoreUrlsConfig();
    }

    /**
     * 权限不足处理器
     */
    @Bean
    public MyAccessDeniedHandler myAccessDeniedHandler(){
        return new MyAccessDeniedHandler();
    }

    /**
     * 未登录或失效处理器
     */
    @Bean
    public MytAuthenticationEntryPoint mytAuthenticationEntryPoint(){
        return new MytAuthenticationEntryPoint();
    }

    /**
     * token前置过滤器
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        // 从配置文件中导入设置白名单
        for (String url: ignoreUrlsConfig().getUrls()){
            System.out.println("正在读取白名单地址 -> " + url);
            // 设置白名单
            registry.antMatchers(url).permitAll();
        }

        // 允许跨域的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS)
                .permitAll();

        // 其他请求都必须认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                // 关闭csrf 和 session管理
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                // 拒绝访问处理器
                .accessDeniedHandler(myAccessDeniedHandler())
                .authenticationEntryPoint(mytAuthenticationEntryPoint())
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }
}
