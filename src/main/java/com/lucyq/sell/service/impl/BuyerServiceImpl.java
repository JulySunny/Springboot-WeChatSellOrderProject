package com.lucyq.sell.service.impl;

import com.lucyq.sell.dto.OrderDTO;
import com.lucyq.sell.enums.ResultEnum;
import com.lucyq.sell.exception.SellException;
import com.lucyq.sell.service.BuyerService;
import com.lucyq.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 杨强
 * @Date: 2019/6/8 0:51
 * @Version 1.0
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    OrderService orderService;

    //查询订单详情
    @Override
    public OrderDTO findOrderOne(String openId, String orderId) {
        OrderDTO orderDTO = getOrderDTO(openId, orderId);
        return orderDTO;
    }
    //取消订单
    @Override
    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO = getOrderDTO(openId, orderId);
        OrderDTO dto = orderService.cancel(orderDTO);
        return dto;
    }


    private OrderDTO getOrderDTO(String openId, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO==null){
            log.error("【查询订单】 查询订单不存在" );
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (orderDTO.getBuyerOpenid().equalsIgnoreCase(openId)){
            log.error("【查询订单】 买家id与订单id不符" );
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

}
