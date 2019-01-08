package com.curtain.seckill.repository;

import com.curtain.seckill.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Curtain
 * @date 2019/1/3 14:52
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
