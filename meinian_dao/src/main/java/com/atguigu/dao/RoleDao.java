package com.atguigu.dao;


import com.atguigu.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 持久层Dao接口
 */
@Repository
public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

}
