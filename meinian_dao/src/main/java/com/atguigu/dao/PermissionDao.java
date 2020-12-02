package com.atguigu.dao;

import com.atguigu.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 持久层Dao接口
 */
@Repository
public interface PermissionDao {

    Set<Permission> findPermissionsByRoleId(Integer roleId);

}
