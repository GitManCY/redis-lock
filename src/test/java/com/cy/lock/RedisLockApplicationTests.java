package com.cy.lock;

import com.cy.lock.jedis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisLockApplicationTests {

    @Autowired
    RedisService redisService;

    @Test
    void contextLoads() {
        redisService.seckill();
    }

}
