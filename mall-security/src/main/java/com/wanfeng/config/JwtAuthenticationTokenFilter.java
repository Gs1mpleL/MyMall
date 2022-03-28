package com.wanfeng.config;

import com.wanfeng.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wanfeng
 * @created 2022/3/28 17:54
 * @package com.wanfeng.config
 */

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("进入Token前置过滤器！");
        String authHeader = request.getHeader(this.tokenHeader);
        System.out.println("获取Authorization的请求头内容 = "+authHeader);
        System.out.println("开始判断Authorization是不是null，和是不是已Bearer 开头");
            if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            System.out.println("是的呢");
            // The part after "Bearer "
            String authToken = authHeader.substring(this.tokenHead.length());
            System.out.println("截取后获得当前登录携带的token->" + authToken);
                System.out.println("开始从token中解析出Username");
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            System.out.println("从token中解析出来username->" + username);
            // 存在用户名 且 未登录
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 执行登录service登录获得UserDetails
                // 实现接口后就可以执行这个方法
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    System.out.println("验证Token");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}", username);
                    // 在环境中添加user
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
