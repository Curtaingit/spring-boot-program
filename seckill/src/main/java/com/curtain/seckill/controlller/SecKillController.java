package com.curtain.seckill.controlller;

import com.crossoverjie.distributed.annotation.SpringControllerLimit;
import com.curtain.seckill.Exception.SecKillException;
import com.curtain.seckill.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Curtain
 * @date 2019/1/3 15:31
 */

@RestController
@RequestMapping("/")
public class SecKillController {

    private Logger logger = LoggerFactory.getLogger(SecKillController.class);

    @Autowired
    private OrderService orderService;

    @SpringControllerLimit(errorCode = 200)
    @RequestMapping("/createOrder/{pid}")
    public String createOrder(@PathVariable int pid) {
        try {
            Integer order = orderService.createOrder(pid);
            if (order!=null){
                return "操作成功";
            }

        } catch (SecKillException se) {
            logger.error(se.getMessage());
        }catch (ObjectOptimisticLockingFailureException oe){
            //todo：暂时不确定为什么乐观锁 会延迟到这里才触发。 先采用这样的解决方案
            //删除错误的订单
            orderService.deleteWrongOrder();
        }catch (Exception e) {
            logger.error("createWrongOrder-Exception",e);
        }
        return "创建失败";
    }

    @RequestMapping("/createOrderByRedis/{pid}")
    public Integer createOrderByRedis(@PathVariable int pid) {
        int id = 0;
        try {
            id = orderService.createOrderByRedis(pid);
        } catch (SecKillException se) {
            return null;
        } catch (Exception e) {
            logger.error("Exception", e);

        }
        return id;
    }
}
