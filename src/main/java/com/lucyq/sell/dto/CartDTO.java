package com.lucyq.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 22:40
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {


    private String productId;

    private Integer productQuantity;
}
