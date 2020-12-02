package com.atguigu.service;

import com.atguigu.pojo.User;

/**
 * 用户服务接口
 */
public interface UserService {

    User findUserByUsername(String username);
}
