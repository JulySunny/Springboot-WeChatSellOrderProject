package com.lucyq.sell.repository;

import com.lucyq.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 0:15
 * @Version 1.0
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>{

    //根据商品上架状态查询商品
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
