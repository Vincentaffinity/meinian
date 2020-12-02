package com.atguigu.dao;

import com.atguigu.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * 持久层Dao接口
 */
@Repository
public interface UserDao {

    User findUserByUsername(String username);
}
