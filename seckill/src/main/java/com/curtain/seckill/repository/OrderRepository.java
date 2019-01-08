package com.curtain.seckill.repository;

import com.curtain.seckill.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Curtain
 * @date 2019/1/3 14:51
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

}
