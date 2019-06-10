package com.lucyq.sell.service;

import com.lucyq.sell.dto.OrderDTO;

/**
 * @Author: 杨强
 * @Date: 2019/6/10 23:45
 * @Version 1.0
 */
public interface PayService {

    void create(OrderDTO orderDTO);
}
