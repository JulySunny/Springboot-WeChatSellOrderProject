package com.lucyq.sell.repository;

import com.lucyq.sell.dataobject.ProductCategory;
import net.minidev.json.JSONValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: 杨强
 * @Date: 2019/6/4 0:34
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory =new ProductCategory();
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(3);
        repository.save(productCategory);

    }

    @Test
    public void findOne() {
        Optional<ProductCategory> optional = repository.findById(1);
        if (optional.isPresent()) {
            ProductCategory productCategory = optional.get();
            System.out.println(productCategory.toString());
        }
    }
    @Test
    public void findByIds() {
        List<Integer> list = Arrays.asList(1,2);
        List<ProductCategory> optional = repository.findByCategoryTypeIn(list);
        System.out.println(JSONValue.toJSONString(optional));
    }

}