package com.lucyq.sell.controller;

import com.lly835.bestpay.rest.type.Post;
import com.lucyq.sell.VO.ResultVO;
import com.lucyq.sell.converter.OrderForm2OrderDTOConverter;
import com.lucyq.sell.dto.OrderDTO;
import com.lucyq.sell.enums.ResultEnum;
import com.lucyq.sell.exception.SellException;
import com.lucyq.sell.form.OrderForm;
import com.lucyq.sell.service.BuyerService;
import com.lucyq.sell.service.OrderService;
import com.lucyq.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 杨强
 * @Date: 2019/6/7 23:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //1.创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> createOrder(@Valid OrderForm orderForm,
                                                    BindingResult bindingResult){
        //判断表单是否正确
        if (bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确 order={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO =
         OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getList())){
            log.error("【创建订单】 购物车不能为空",orderDTO);
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO reuslt = orderService.create(orderDTO);
        Map<String,String> map =new HashMap<>();
        map.put("orderId", reuslt.getOrderId());
        return ResultVOUtil.success(map);
    }

    //2.订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(
            @RequestParam("openId") String openId,
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size
    ){
        if (StringUtils.isEmpty(openId)){
            log.error("【查询订单列表】 openid为空" );
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request =new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openId, request);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //3.订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId ){
        //TODO 不安全的做法:随便一个人传一个openid就能查询,这样应该改进
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);

    }
    //4.取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId ){
        //TODO 不安全的做法:随便一个人传一个openid就能查询,这样应该改进
        OrderDTO orderDTO = buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();

    }
}
