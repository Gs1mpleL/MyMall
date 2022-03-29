package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.pojo.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/29 21:46
 * @package com.wanfeng.mapper
 */
public interface PmsProductLadderMapper extends BaseMapper<PmsProductLadder> {
    int insertList(@Param("list") List<PmsProductLadder> productLadderList);
}
