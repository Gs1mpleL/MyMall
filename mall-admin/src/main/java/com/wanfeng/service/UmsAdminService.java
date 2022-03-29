package com.wanfeng.service;

import com.wanfeng.dto.UmsAdminParam;
import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.pojo.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/28 19:43
 * @package com.wanfeng.service
 */
public interface UmsAdminService {

    String login(UmsAdminParam umsAdminParam);

    UserDetails loginByUsername(String username);

    UmsAdmin getAdminFromRedisOrDataBase(String username);

    UmsAdmin register(UmsAdminParam umsAdminParam);

    String refreshToken(String token);

    List<UmsAdmin> list(String keyword, Integer pageNum, Integer pageSize);

    int updateById(Long id, UmsAdmin umsAdmin);

    int delById(Long id);

    List<UmsRole> getRoleInfoById(Long id);
}
