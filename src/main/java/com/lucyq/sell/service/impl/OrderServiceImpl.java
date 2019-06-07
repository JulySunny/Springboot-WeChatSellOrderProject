package com.lucyq.sell.service.impl;

import com.lucyq.sell.converter.OrderMaster2OrderDTOConverter;
import com.lucyq.sell.dataobject.OrderDetail;
import com.lucyq.sell.dataobject.OrderMaster;
import com.lucyq.sell.dataobject.ProductInfo;
import com.lucyq.sell.dto.CartDTO;
import com.lucyq.sell.dto.OrderDTO;
import com.lucyq.sell.enums.OrderStatusEnum;
import com.lucyq.sell.enums.PayStatusEnum;
import com.lucyq.sell.enums.ResultEnum;
import com.lucyq.sell.exception.SellException;
import com.lucyq.sell.repository.OrderDetailRepository;
import com.lucyq.sell.repository.OrderMasterRepository;
import com.lucyq.sell.service.OrderService;
import com.lucyq.sell.service.ProductService;
import com.lucyq.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author: 杨强
 * @Date: 2019/6/5 21:55
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //创建订单Id
        String orderId = KeyUtils.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1.查询商品(数量,价格)--注意商品价格一定要从数据库拿.不能直接用前端 传过来的;
        for (OrderDetail orderDetail : orderDTO.getList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单总价 单价乘以数量
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtils.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }

        //3订单入库(master和detail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);

        //orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.下单成功,扣库存
        List<CartDTO> cartDTOList;
        cartDTOList = orderDTO.getList().stream()
                .map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        return orderDTO;
    }

    /*查询单个订单*/
    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster==null){
            //订单为空
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            //订单详情为空
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setList(orderDetailList);
        return orderDTO;
    }

    /*查询订单列表*/
    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        //转换器
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalPages());
        return orderDTOPage;
    }

    /*取消订单*/
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster =new OrderMaster();
        //先判断订单的状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确， orderId={} ，orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster master = orderMasterRepository.save(orderMaster);
        if (master==null){
            log.error("【取消订单】 更新失败 orderMaster={}" ,orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getList())){
            log.error("【取消订单】 订单单重无商品，orderDTO={}",orderDTO);
        }
        //cartDTO库存数据传输对象
        List<CartDTO> list = orderDTO.getList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        //如果已支付,需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SECCESS)){
            //TODO
        }
        return orderDTO;
    }

    /*完结订单*/
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {

        //查询订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW)){
            log.error("【完结订单】订单状态不正确，orderDTO={},orderStatus={}",orderDTO,orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster update = orderMasterRepository.save(orderMaster);
        if (null==update){
            log.error("【完结订单】 订单更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //查询订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW)){
            log.error("【支付订单】订单状态不正确，orderDTO={},orderStatus={}",orderDTO,orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断订单支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】 订单支付状态不正确",orderDTO,orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(PayStatusEnum.SECCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster update = orderMasterRepository.save(orderMaster);
        if (null==update){
            log.error("【支付订单】 订单支付失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
