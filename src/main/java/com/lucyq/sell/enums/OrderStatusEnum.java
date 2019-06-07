package com.lucyq.sell.enums;

import lombok.Getter;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 21:31
 * @Version 1.0
 */
@Getter
public enum OrderStatusEnum {

    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消");

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
