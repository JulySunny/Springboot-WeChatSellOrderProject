package com.lucyq.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: 杨强
 * @Date: 2019/6/10 23:54
 * @Version 1.0
 */
@Component
public class WechatPayConfig {

    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){
        WxPayH5Config wxPayH5Config =new WxPayH5Config() ;

        wxPayH5Config.setAppId(accountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(accountConfig.getMpAppSecret());
        wxPayH5Config.setMchId(accountConfig.getMchId());
        wxPayH5Config.setMchKey(accountConfig.getMchKey());
        wxPayH5Config.setKeyPath(accountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(accountConfig.getNotifyUrl());

        BestPayServiceImpl bestPayService =new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;

    }
}
