package com.cy.lock.JedisDemo.service;

import com.cy.lock.JedisDemo.entity.User;

public interface UserService {
    /**
     * redis String类型
     * 需求：用户需要输入一个key
     * 先判断Redis中是否存在该数据
     * 如果存在，在Redis中进行查询并返回
     * 如果不存在 在MySql数据库查询 将结果赋给Redis并返回
     */
    String getString(String key);


    /**
     * 测试String类型
     * 需求：用户输入一个redis数据 该key的有效期为28小时
     */
    void expireString(String key, String value);

    /**
     * Hsah类型演示
     * 需求分析： 根据用户ID查询用户信息
     * 用户在前端传入一个ID编写
     * 根据用户的ID 查询用户中是否存在用户的对象信息
     * 如果存在，在Redis中进行查询并返回
     * 如果不存在 在MySql数据库查询 将结果赋给Redis并返回
     */
    User selectById(String id);

}
