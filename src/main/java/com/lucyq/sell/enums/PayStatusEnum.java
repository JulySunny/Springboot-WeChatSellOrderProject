package com.lucyq.sell.enums;

import lombok.Getter;

/**
 * 订单支付的枚举类
 * @Author: 杨强
 * @Date: 2019/6/5 21:33
 * @Version 1.0
 */
@Getter
public enum PayStatusEnum {
    WAIT(0,"等待支付"),
    SECCESS(1,"支付成功"),
    ;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
