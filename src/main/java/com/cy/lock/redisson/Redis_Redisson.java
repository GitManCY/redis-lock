package com.cy.lock.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


//redis & redission

@RestController
@Slf4j
public class Redis_Redisson {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Redisson redisson;

    @RequestMapping("/deductStock")
    public String deductStock() throws Exception {
        //单体项目锁住就行（非分布式集群部署）
//        synchronized (this) {
//            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
//            if (stock > 0) {
//                int realStock = stock - 1;
//                redisTemplate.opsForValue().set("stock", realStock + "");
//                log.info("扣件成功,剩余库存:" + realStock);
//            } else {
//                log.info("扣件失败，库存不足");
//            }
//        }
//        log.info(redisson.toString());//org.redisson.Redisson@7c20e192

        //分布式锁
        String lockKey = "product_001";
        String clientId = UUID.randomUUID().toString();
        RLock lock = redisson.getLock(lockKey);
        try {
//            //设置过期防止死锁 上锁和过期一起源子性
//            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);//setnx key value
//            if (!result) {
//                return "error";
//            }
            lock.lock(30, TimeUnit.SECONDS);
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣件成功,剩余库存:" + realStock);
            } else {
                log.info("扣件失败，库存不足");
            }
        } finally {
            //释放锁
            lock.unlock();
//            if (clientId.equals(redisTemplate.opsForValue().get(lockKey))) {
//                //线程进入的先后顺序不同 防止不同线程释放了别人的锁
//                redisTemplate.delete(lockKey);
//            }
        }
        return "end";
    }

}

