package com.lucyq.sell.controller;

import com.lucyq.sell.VO.ProductInfoVO;
import com.lucyq.sell.VO.ProductVO;
import com.lucyq.sell.VO.ResultVO;
import com.lucyq.sell.dataobject.ProductCategory;
import com.lucyq.sell.dataobject.ProductInfo;
import com.lucyq.sell.service.CatagoryService;
import com.lucyq.sell.service.ProductService;
import com.lucyq.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 0:32
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private CatagoryService catagoryService;

    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //2.查询类目(一次性查询)
        List<Integer> catagoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = catagoryService.findByCategoryTypeIn(catagoryTypeList);
        //3.数据拼装
        List<ProductVO> productVOList =new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList =new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO =new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        ResultVO resultVO = ResultVOUtil.success(productVOList);
        return resultVO;
    }
}
