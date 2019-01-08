package com.curtain.seckill.service;

import com.curtain.seckill.Exception.SecKillException;
import com.curtain.seckill.domain.Product;
import com.curtain.seckill.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Curtain
 * @date 2019/1/3 14:55
 */

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//    @Cacheable(value = "test", key = "#id")
    public Product getProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new SecKillException("商品未找到");
    }

//    @CachePut(value = "test",key = "#product.id")
    public Product updateStock(Product product) {
        System.out.println(product.getVersion());
        product.setStock(product.getStock()-1);
        Product save = productRepository.save(product);
        System.out.println(save.getVersion());
        return save;

    }
}
