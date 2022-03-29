package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.pojo.UmsAdminRoleRelation;
import com.wanfeng.pojo.UmsResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wanfeng
 * @create 2022/3/29 11:19
 * @package com.wanfeng.mapper
 */
@Mapper
public interface AdminRoleResourceMapper extends BaseMapper<UmsAdminRoleRelation> {
    /**
     * 根据用户Id获取可用资源
     */
    List<UmsResource> getResourceListByAdminId(Long adminId);
}
