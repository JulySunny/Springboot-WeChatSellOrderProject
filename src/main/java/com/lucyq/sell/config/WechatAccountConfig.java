package com.lucyq.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 账户相关的配置类
 * @Author: 杨强
 * @Date: 2019/6/8 14:36
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 公众号id
     */
    private String mpAppId;
    /**
     * 公众号密钥
     */
    private String mpAppSecret;
    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信支付异步通知的地址
     */
    private String notifyUrl;
}
