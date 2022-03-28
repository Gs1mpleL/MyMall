package com.wanfeng.component;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * 动态权限控制过滤器
 * @author wanfeng
 * @created 2022/3/28 23:22
 * @package com.wanfeng.component
 */
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return null;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return null;
    }
}
