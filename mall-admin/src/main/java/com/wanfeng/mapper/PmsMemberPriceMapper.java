package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.pojo.PmsMemberPrice;
import com.wanfeng.pojo.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/30 10:24
 * @package com.wanfeng.mapper
 */
public interface PmsMemberPriceMapper extends BaseMapper<PmsMemberPrice> {
    int insertList(@Param("list") List<PmsMemberPrice> pmsMemberPrices);
}
