package com.cy.lock.JedisDemo.service;

import com.cy.lock.JedisDemo.entity.User;
import com.cy.lock.JedisDemo.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public String getString(String key) {
        String val = null;
        Jedis jedis = jedisPool.getResource();
        if (jedis.exists(key)) {
            log.info("{}", "查询Redis中的数据");
            val = jedis.get(key);
        } else {
            String name = "chengyang";
            log.info("{}", "查询MySql中的数据");
            jedis.set(key, name);
            val = name;
        }
        jedis.close();
        return val;
    }

    @Override
    public void expireString(String key, String value) {
        Jedis jedis = jedisUtil.getJedis();
        jedis.set(key, value);
        jedis.expire(key, 20);
        log.info("测试数据添加成功");
        jedisUtil.close(jedis);
    }

    @Override
    public User selectById(String id) {
        String key = "user" + id;
        Jedis jedis = jedisUtil.getJedis();
        User user = null;
        if (jedis.exists(key)) {
            log.info("{}", "查询Redis中的数据");
            Map<String, String> map = jedis.hgetAll(key);
            user = new User();
            user.setId(map.get("id"));
            user.setName(map.get("name"));
            user.setAge(Integer.parseInt(map.get("age")));
        } else {
            log.info("{}", "查询MySql中的数据");
            user = new User("id", "chengyang", 20);
            ConcurrentHashMap concurrentHashMap = userToMap(user);
            jedis.hmset(key, concurrentHashMap);
            log.info("{}", "往Redis中存数据");
        }
        return user;
    }

    private ConcurrentHashMap userToMap(User user) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("id", user.getId());
        concurrentHashMap.put("name", user.getName());
        concurrentHashMap.put("age", String.valueOf(user.getAge()));
        return concurrentHashMap;
    }
}
