package com.lucyq.sell.repository;

import com.lucyq.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 21:38
 * @Version 1.0
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    //按照买家OpenId查询买家订单
    Page<OrderMaster> findByBuyerOpenid(String s, Pageable pageable);
}
