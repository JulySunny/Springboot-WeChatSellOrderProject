package com.lucyq.sell.service;

import com.lucyq.sell.dataobject.ProductInfo;
import com.lucyq.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 0:19
 * @Version 1.0
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在上架的商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
