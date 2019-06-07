package com.lucyq.sell.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品状态枚举
 * @Author: 杨强
 * @Date: 2019/6/5 0:22
 * @Version 1.0
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
