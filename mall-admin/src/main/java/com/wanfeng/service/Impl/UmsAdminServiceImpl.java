package com.wanfeng.service.Impl;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.mapper.UmsAdminMapper;
import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.pojo.UmsResource;
import com.wanfeng.pojo.UmsRole;
import com.wanfeng.service.RedisService;
import com.wanfeng.service.UmsAdminCacheService;
import com.wanfeng.service.UmsAdminService;
import com.wanfeng.utils.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Date;
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
        System.out.println("----------login()-----------");
        String username = umsAdminParam.getUsername();
        String password = umsAdminParam.getPassword();
        // ????????????token
        String s;
        try {
            UserDetails userDetails = loginByUsername(username);
            System.out.println("??????loginByUsername()??????????????????");
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                System.out.println("login()??????????????????!");
                throw new UsernameNotFoundException("????????????!");
            }
            if (!userDetails.isEnabled()) {
                System.out.println("login()???????????????????????????!");
                throw new UsernameNotFoundException("?????????????????????");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("?????????????????????SecurityContextHolder");
            s = jwtTokenUtil.generateToken(userDetails);
            System.out.println("???????????????token" + s);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return s;
    }


    /**
     * ?????????UserDetailsService??????
     */
    @Override
    public UserDetails loginByUsername(String username) {
        System.out.println("------------------loginByUsername()??????---------------------");
        UmsAdmin umsAdmin = getAdminFromRedisOrDataBase(username);
        if(umsAdmin != null){
            List<UmsResource> resourceById = umsResourceService.getResourceById(umsAdmin.getId());
            System.out.println("???????????????????????????????????????????????????:" + resourceById);
            return new AdminUserDetails(umsAdmin,resourceById);
        }
        // ??????????????????????????????????????????????????????
        System.out.println("??????????????????????????????????????????????????????");
        throw new UsernameNotFoundException("????????????????????????");
    }

    /**
     * ???????????????????????????????????????????????????????????? ??????????????????
     */
    @Override
    public UmsAdmin getAdminFromRedisOrDataBase(String username) {
        UmsAdmin umsAdmin = umsAdminCacheService.getAdmin(username);
        if(umsAdmin!=null){
            System.out.println("?????????????????????????????????");
            return umsAdmin;
        }
        System.out.println("???Redis?????????????????????");
        UmsAdmin umsAdminFromDb = umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("username", username));
        if(umsAdminFromDb!=null){
            System.out.println("????????????????????????????????????,?????????redis");
            // ????????????????????????????????????redis
            umsAdminCacheService.setAdmin(umsAdminFromDb);
        }
        return umsAdminFromDb;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        String username = umsAdmin.getUsername();
        // ??????????????????
        UmsAdmin admin = umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("username", username));
        if(admin!=null){
            return null;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
        umsAdminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }

    @Override
    public List<UmsAdmin> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<UmsAdmin>queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",keyword);
        return umsAdminMapper.selectList(queryWrapper);
    }

    @Override
    public int updateById(Long id, UmsAdmin umsAdmin) {
        umsAdmin.setId(id);
        UmsAdmin adminFromDb = umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("id", id));
        if(!adminFromDb.getPassword().equals(new BCryptPasswordEncoder().encode(umsAdmin.getPassword()))){
            umsAdmin.setPassword(new BCryptPasswordEncoder().encode(umsAdmin.getPassword()));
        }
        return umsAdminMapper.updateById(umsAdmin);
    }

    @Override
    public int delById(Long id) {
        return umsAdminMapper.deleteById(id);
    }

    @Override
    public List<UmsRole> getRoleInfoById(Long id) {
        return umsAdminMapper.getRoleInfoById(id);
    }

}
