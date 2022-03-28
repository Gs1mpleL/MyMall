package com.wanfeng.service.Impl;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.mapper.UmsAdminMapper;
import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.service.UmsAdminService;
import com.wanfeng.utils.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author wanfeng
 * @created 2022/3/28 19:44
 * @package com.wanfeng.service.Impl
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String login(UmsAdminParam umsAdminParam) {
        String username = umsAdminParam.getUsername();
        String password = umsAdminParam.getPassword();
        // 要返回的token
        String s;
        try {
            UserDetails userDetails = loginByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new UsernameNotFoundException("密码错误!");
            }
            if (!userDetails.isEnabled()) {
                throw new UsernameNotFoundException("用户已经被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            s = jwtTokenUtil.generateToken(userDetails);

        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return s;
    }


    /**
     * 重写的UserDetailsService方法
     */
    @Override
    public UserDetails loginByUsername(String username) {
        UmsAdmin umsAdmin = getAdminFromRedisOrDataBase(username);
        if(umsAdmin != null){
            System.out.println("这里缺少用户赋权的操作,可能会报缺少角色的bug");
            return new AdminUserDetails(umsAdmin,null);
        }
        // 在数据库和缓存中都没有找到需要的数据
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 根据用户名去缓存中获取用户信息，获取不到 去数据库查找
     */
    @Override
    public UmsAdmin getAdminFromRedisOrDataBase(String username) {
        System.out.println("这里少一项从redis中获取用户信息的操作");
        return umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("username", username));
    }
}
