package com.wanfeng.service.Impl;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.mapper.UmsAdminMapper;
import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.pojo.UmsResource;
import com.wanfeng.service.RedisService;
import com.wanfeng.service.UmsAdminCacheService;
import com.wanfeng.service.UmsAdminService;
import com.wanfeng.utils.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/28 19:44
 * @package com.wanfeng.service.Impl
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private UmsAdminMapper umsAdminMapper;
    private JwtTokenUtil jwtTokenUtil;
    private UmsResourceServiceImpl umsResourceService;
    private UmsAdminCacheService umsAdminCacheService;

    @Autowired
    public UmsAdminServiceImpl(UmsAdminMapper umsAdminMapper, JwtTokenUtil jwtTokenUtil, UmsResourceServiceImpl umsResourceService, UmsAdminCacheService umsAdminCacheService) {
        this.umsAdminMapper = umsAdminMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.umsResourceService = umsResourceService;
        this.umsAdminCacheService = umsAdminCacheService;
    }


    @Override
    public String login(UmsAdminParam umsAdminParam) {
        String username = umsAdminParam.getUsername();
        String password = umsAdminParam.getPassword();
        // 要返回的token
        String s;
        try {
            UserDetails userDetails = loginByUsername(username);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
            List<UmsResource> resourceById = umsResourceService.getResourceById(umsAdmin.getId());
            return new AdminUserDetails(umsAdmin,resourceById);
        }
        // 在数据库和缓存中都没有找到需要的数据
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 根据用户名去缓存中获取用户信息，获取不到 去数据库查找
     */
    @Override
    public UmsAdmin getAdminFromRedisOrDataBase(String username) {


        UmsAdmin umsAdmin = umsAdminCacheService.getAdmin(username);
        if(umsAdmin!=null){
            return umsAdmin;
        }
        System.out.println("在Redis中未查询到用户");
        UmsAdmin umsAdminFromDb = umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("username", username));
        if(umsAdminFromDb!=null){
            // 在数据库查到后把用户存入redis
            umsAdminCacheService.setAdmin(umsAdminFromDb);
        }
        return umsAdminFromDb;
    }
}
