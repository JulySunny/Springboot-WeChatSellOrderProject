package com.lucyq.sell.VO;

import lombok.Data;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 0:38
 * @Version 1.0
 */
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
