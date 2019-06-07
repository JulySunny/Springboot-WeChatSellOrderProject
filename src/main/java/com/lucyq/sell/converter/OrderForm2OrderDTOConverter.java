package com.lucyq.sell.converter;

import com.alibaba.fastjson.JSON;
import com.lucyq.sell.dataobject.OrderDetail;
import com.lucyq.sell.dto.CartDTO;
import com.lucyq.sell.dto.OrderDTO;
import com.lucyq.sell.enums.ResultEnum;
import com.lucyq.sell.exception.SellException;
import com.lucyq.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 杨强
 * @Date: 2019/6/7 23:34
 * @Version 1.0
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO =new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());

        List<OrderDetail> list =new ArrayList<>();
        String items = orderForm.getItems();
        try {
            list=JSON.parseArray(items, OrderDetail.class);
        } catch (Exception e) {
            //如果转换出错了,需要进行异常打印
            log.error("【对象转换】 错误 ,String={}",items);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setList(list);
        return orderDTO;
    }
}
