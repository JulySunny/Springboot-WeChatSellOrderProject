package com.lucyq.sell.utils;

import com.lucyq.sell.VO.ResultVO;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 1:10
 * @Version 1.0
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO =new ResultVO();
        resultVO.setCode(0);
        resultVO.setData(object);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO fail(Integer code,String msg){
        ResultVO resultVO =new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
