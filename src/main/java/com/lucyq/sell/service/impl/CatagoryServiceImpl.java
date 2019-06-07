package com.lucyq.sell.service.impl;

import com.lucyq.sell.dataobject.ProductCategory;
import com.lucyq.sell.repository.ProductCategoryRepository;
import com.lucyq.sell.service.CatagoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 杨强
 * @Date: 2019/6/4 0:50
 * @Version 1.0
 */
@Service
public class CatagoryServiceImpl implements CatagoryService {
    @Autowired
    ProductCategoryRepository repository;
    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
