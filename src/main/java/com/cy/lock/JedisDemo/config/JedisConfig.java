package com.cy.lock.JedisDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Component
public class JedisConfig {

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool();
    }

}
