package com.wanfeng.service;

import com.wanfeng.dto.OmsOrderQueryParam;
import com.wanfeng.pojo.OmsOrder;

import java.util.List;

/**
 * @author wanfeng
 * @created 2022/3/31 12:55
 * @package com.wanfeng.service
 */
public interface OmsOrderService {
    List<OmsOrder> getListByPage(OmsOrderQueryParam omsOrderQueryParam, Integer pageNum, Integer pageSize);
}
