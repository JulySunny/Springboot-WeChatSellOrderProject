package com.lucyq.sell.service;

import com.lucyq.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 * @Author: 杨强
 * @Date: 2019/6/4 0:48
 * @Version 1.0
 */
public interface CatagoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
