package com.lucyq.sell.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 *  用于表单提交的类
 * @Author: 杨强
 * @Date: 2019/6/7 23:14
 * @Version 1.0
 */
@Data
@Accessors
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /**\
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    private String address;


    /**
     *买家微信openId
     */
    @NotEmpty(message = "openId必填")
    private String openId;

    /**
     * 买家购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;

}
