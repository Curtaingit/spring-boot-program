package com.curtain.seckill.service;

import com.curtain.seckill.Exception.SecKillException;
import com.curtain.seckill.domain.Order;
import com.curtain.seckill.domain.Product;
import com.curtain.seckill.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Curtain
 * @date 2019/1/3 14:55
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisLock redisLock;

    private static final Map<String, Integer> map = new HashMap();

    public Integer createOrder(int pid) {

        //加锁
        long time = System.currentTimeMillis() + 10000;
        boolean lock = redisLock.lock(String.valueOf(pid), String.valueOf(time));

        if (!lock) {
            throw new SecKillException("获取锁失败");
        }
        //校验库存
        Product product = checkStock(pid);

        //扣库存
        saleStock(product);

        //创建订单并返回id
        Integer order = saveOrder(product);

        //解锁
        redisLock.unlock(String.valueOf(pid), String.valueOf(time));

        //创建订单并返回id
        return order;
    }

    public int createOrderByRedis(int pid) {
        //校验库存
        Product product = checkStockByRedis(pid);

        //扣库存
        saleProductByRedis(product);

        //创建订单并返回id
        return saveOrder(product);
    }

    private int saveOrder(Product product) {

        Order order = new Order();
        order.setCreateTime(System.currentTimeMillis());
        order.setPid(product.getId());

        Integer id = orderRepository.save(order).getId();

        //保存线程名 与创建完成的订单id      解决ObjectOptimisticLockingFailureException异常延迟触发。
        map.put(Thread.currentThread().getName(), id);

        return id;

    }


    private void saleStock(Product product) {
        productService.updateStock(product);
    }


    private Product checkStock(int pid) {
        Product product = productService.getProduct(pid);
        if (product.getStock() <= 0) {
            throw new SecKillException("库存不足");
        }
        return product;
    }

    /**
     * 通过redis获取商品数据
     *
     * @param pid
     * @return
     */
    private Product checkStockByRedis(int pid) {
        Integer stock = 0;
        Integer version = 0;
        try {
            stock = Integer.parseInt(redisTemplate.opsForValue().get("product_stock" + pid));
            version = Integer.parseInt(redisTemplate.opsForValue().get("product_version" + pid));
        } catch (NumberFormatException e) {
            Product product = productService.getProduct(pid);
            redisTemplate.opsForValue().set("product_stock" + pid, String.valueOf(product.getStock()));
            redisTemplate.opsForValue().set("product_version" + pid, String.valueOf(product.getStock()));
            stock = product.getStock();
        }


        if (stock <= 0) {
            throw new SecKillException("库存不足");
        }

        Product product = new Product();
        product.setId(pid);
        product.setStock(stock);
        product.setVersion(version);

        return product;
    }

    private void saleProductByRedis(Product product) {
        product.setStock(product.getStock() - 1);
        product = productService.updateStock(product);

        redisTemplate.opsForValue().set("product_stock" + product.getId(), String.valueOf(product.getStock()));
        redisTemplate.opsForValue().set("product_version" + product.getId(), String.valueOf(product.getVersion()));
    }

    public void deleteWrongOrder() {
        Integer id = map.get(Thread.currentThread().getName());
        orderRepository.deleteById(id);
    }
}
