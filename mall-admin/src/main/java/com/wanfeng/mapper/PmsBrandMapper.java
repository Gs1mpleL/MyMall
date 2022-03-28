package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.pojo.PmsBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/27 20:30
 * @package com.wanfeng.mapper
 */
@Mapper
public interface PmsBrandMapper extends BaseMapper<PmsBrand> {
    int updateShowStatus(@Param("ids")List<Long> ids, @Param("showStatus")Integer showStatus);

    int updateFactoryStatus(@Param("ids")List<Long> ids, @Param("factoryStatus")Integer factoryStatus);
}
