package com.wanfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfeng.dto.PmsProductCategoryWithChildrenItem;
import com.wanfeng.pojo.PmsProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author wanfeng
 * @create 2022/3/27 13:34
 * @package com.wanfeng.mapper
 */
@Mapper
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    int selectLevelById(Long parentId);

    int updateNavStatus( @Param("ids")List<Long> ids, @Param("navStatus")Integer navStatus);

    int updateShowStatus(@Param("ids")List<Long> ids,@Param("showStatus") Integer navStatus);

    List<PmsProductCategoryWithChildrenItem> getListWithChildren();
}
