package com.lucyq.sell.converter;

import com.lucyq.sell.dataobject.OrderMaster;
import com.lucyq.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 * @Author: 杨强
 * @Date: 2019/6/5 23:09
 * @Version 1.0
 */
public class OrderMaster2OrderDTOConverter {


    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){

        List<OrderDTO> list = orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return list;
    }

}
