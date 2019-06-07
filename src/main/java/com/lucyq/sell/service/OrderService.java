package com.lucyq.sell.service;

import com.lucyq.sell.dataobject.OrderDetail;
import com.lucyq.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 订单的service
 * @Author: 杨强
 * @Date: 2019/6/5 21:48
 * @Version 1.0
 */
public interface OrderService {

    /*创建订单*/
    OrderDTO create(OrderDTO orderDTO);

    /*查询单个订单*/
    OrderDTO findOne(String orderId);

    /*查询订单列表*/
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

    /*取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);

    /*完结订单*/
    OrderDTO finish(OrderDTO orderDTO);

    /*支付订单*/
    OrderDTO paid(OrderDTO orderDTO);
}
