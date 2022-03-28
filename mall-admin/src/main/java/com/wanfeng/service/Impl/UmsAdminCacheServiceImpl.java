package com.wanfeng.service.Impl;

import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.service.RedisService;
import com.wanfeng.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author wanfeng
 * @created 2022/3/28 23:46
 * @package com.wanfeng.service.Impl
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    private RedisService redisService;

    @Autowired
    public UmsAdminCacheServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void setAdmin(UmsAdmin umsAdmin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + umsAdmin.getUsername();
        redisService.set(key,umsAdmin,REDIS_EXPIRE);
    }

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisService.get(key);
    }
}
