package com.wanfeng.service.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanfeng.dto.PmsProductParam;
import com.wanfeng.dto.PmsProductResult;
import com.wanfeng.mapper.PmsMemberPriceMapper;
import com.wanfeng.mapper.PmsProductLadderMapper;
import com.wanfeng.mapper.PmsProductMapper;
import com.wanfeng.pojo.PmsProduct;
import com.wanfeng.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/27 23:38
 * @package com.wanfeng.service.Impl
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {

    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private PmsProductLadderMapper pmsProductLadderMapper;
    @Autowired
    private PmsMemberPriceMapper memberPriceMapper;



    /**
     * 还有问题 先写别的模块了
     */
    @Override
    public PmsProductResult getById(Long id) {
        PmsProductResult pmsProductResult = pmsProductMapper.getById(id);
        return null;
    }

    @Override
    public int create(PmsProductParam pmsProductParam) {
        // 创建商品
        PmsProduct pmsProduct = pmsProductParam;
        pmsProduct.setId(null);
        // 先添加信息
        pmsProductMapper.insert(pmsProduct);
        // 这里应该去mapper中设置获取id后封装到对象，就不写了，直接多查一次
        String name = pmsProduct.getName();
        PmsProduct pmsProduct1 = pmsProductMapper.selectOne(new QueryWrapper<PmsProduct>().eq("name", name));
        // 因为在其他关系表中插入数据需要该商品的id，所以先添加数据后，获得该商品id
        Long id = pmsProduct1.getId();
        System.out.println(pmsProductParam.getProductLadderList());
        // 设置商品阶梯价格
        setConnection(pmsProductLadderMapper, pmsProductParam.getProductLadderList(),id);
        // 设置会员价格
        setConnection(memberPriceMapper,pmsProductParam.getMemberPriceList(),id);
        return 1;
    }


    /**
     * 向数据库插入数据
     * @param mapper 对应数据库的Mapper
     * @param data 插入的data
     * @param id 出入的商品id
     */
    private void setConnection(Object mapper, List data,Long id) {
        if(CollUtil.isEmpty(data)){
            // 没数据直接返回了！
            return;
        }
        try {
            // 设置每条数据的商品id
            for (Object item : data) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                // 清除id
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                // 每条数据添加商品id
                setProductId.invoke(item,id);
            }
            // 执行对应Mapper的插入操作
            Method insertList = mapper.getClass().getMethod("insertList",List.class);
            insertList.invoke(mapper,data);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
