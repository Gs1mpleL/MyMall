package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.pojo.UmsAdmin;
import com.wanfeng.pojo.UmsRole;
import org.apache.ibatis.annotations.Mapper;

import javax.management.relation.Role;
import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/28 19:42
 * @package com.wanfeng.mapper
 */
@Mapper
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {
    List<UmsRole> getRoleInfoById(Long id);
}
