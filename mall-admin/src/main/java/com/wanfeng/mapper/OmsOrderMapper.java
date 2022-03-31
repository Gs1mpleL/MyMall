package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.dto.OmsOrderQueryParam;
import com.wanfeng.pojo.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/31 12:56
 * @package com.wanfeng.mapper
 */
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
    List<OmsOrder> selectByParam(@Param("queryParam") OmsOrderQueryParam omsOrderQueryParam);
}
