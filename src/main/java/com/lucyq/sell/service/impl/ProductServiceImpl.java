package com.lucyq.sell.service.impl;

import com.lucyq.sell.dataobject.ProductInfo;
import com.lucyq.sell.dto.CartDTO;
import com.lucyq.sell.enums.ProductStatusEnum;
import com.lucyq.sell.enums.ResultEnum;
import com.lucyq.sell.exception.SellException;
import com.lucyq.sell.repository.ProductInfoRepository;
import com.lucyq.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 0:19
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    /*减库存*/
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
        }
    }

    /*加库存*/
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result<0){
                //库存小于0,扣库存失败
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }

    }
}
