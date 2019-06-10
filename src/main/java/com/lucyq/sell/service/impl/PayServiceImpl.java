package com.lucyq.sell.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lucyq.sell.dto.OrderDTO;
import com.lucyq.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 杨强
 * @Date: 2019/6/10 23:45
 * @Version 1.0
 */
@Service
@Slf4j
public class PayServiceImpl  implements PayService{

    private static final String ORDER_NAME="微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public void create(OrderDTO orderDTO) {
        //BestPayServiceImpl bestPayService =new BestPayServiceImpl();
        PayRequest payRequest =new PayRequest();

        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】 request={}", JSON.toJSONString(payRequest));
        //发起支付
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】 response={}", JSON.toJSONString(payResponse));
    }
}
