package com.lucyq.sell.service;

import com.lucyq.sell.dto.OrderDTO;

/**
 * @Author: 杨强
 * @Date: 2019/6/8 0:51
 * @Version 1.0
 */
public interface BuyerService {

    //查询订单详情
    OrderDTO findOrderOne(String openId,String orderId);

    //取消订单
    OrderDTO cancelOrder(String openId,String orderId);
}
