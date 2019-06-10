package com.lucyq.sell.controller;

import com.lucyq.sell.dto.OrderDTO;
import com.lucyq.sell.enums.ResultEnum;
import com.lucyq.sell.exception.SellException;
import com.lucyq.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付
 * @Author: 杨强
 * @Date: 2019/6/10 23:41
 * @Version 1.0
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    //创建支付
    @GetMapping("/create")
    public void create(@RequestParam("orderId") String orderId,
                       @RequestParam("returnUrl") String returnUrl){

        //1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付

    }
}
