package com.wanfeng.service;

import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.pojo.UmsAdmin;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wanfeng
 * @created 2022/3/28 19:43
 * @package com.wanfeng.service
 */
public interface UmsAdminService {

    String login(UmsAdminParam umsAdminParam);

    UserDetails loginByUsername(String username);

    UmsAdmin getAdminFromRedisOrDataBase(String username);
}
