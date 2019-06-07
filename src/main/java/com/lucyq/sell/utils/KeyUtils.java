package com.lucyq.sell.utils;

import java.util.Random;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 22:32
 * @Version 1.0
 */
public class KeyUtils {

    /**
     * 生成唯一的主键
     * 格式:时间+随机数
     * @return
     */
    public synchronized static String genUniqueKey(){
        Random random =new Random();
        int number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
