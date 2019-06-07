package com.lucyq.sell.repository;

import com.lucyq.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 杨强
 * @Date: 2019/6/5 21:40
 * @Version 1.0
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String  s);

}
