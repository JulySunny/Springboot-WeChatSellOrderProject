package com.lucyq.sell.repository;

import com.lucyq.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 杨强
 * @Date: 2019/6/4 0:32
 * @Version 1.0
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer>{

    /**
     * 根据类型id集合查询类型
     * @param list
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
}
