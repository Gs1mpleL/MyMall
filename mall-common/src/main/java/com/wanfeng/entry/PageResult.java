package com.wanfeng.entry;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页数据封装类
 *
 * @author macro
 * @date 2019/4/19
 */
@Data
public class PageResult<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> list;

    /**
     * 把list中的内容装入自定义的PageResult
     * @param list 页面
     */
    public static <T> PageResult<T> getPageResult(List<T> list){
        PageResult<T> pageResult = new PageResult<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setList(pageInfo.getList());
        return pageResult;
    }
}
