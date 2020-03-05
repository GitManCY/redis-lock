package com.cy.lock.JedisDemo.controller;

import com.cy.lock.JedisDemo.entity.User;
import com.cy.lock.JedisDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getString")
    public String getString(String key) {
        return userService.getString(key);
    }

    @GetMapping("/expireString")
    public void expireString(String key, String value) {
        userService.expireString(key, value);
    }

    @GetMapping("/selectById")
    public User selectById(String id) {
        User user = userService.selectById(id);
        return user;
    }
}
