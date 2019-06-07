package com.lucyq.sell.dto;

import com.lucyq.sell.dataobject.OrderDetail;
import com.lucyq.sell.enums.OrderStatusEnum;
import com.lucyq.sell.enums.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单的数据传输类
 * @Author: 杨强
 * @Date: 2019/6/5 21:51
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    private List<OrderDetail> list;
}
