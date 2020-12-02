package com.atguigu.dao;


import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    /**
     * 增添套餐游信息
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 绑定套餐和跟团游的多对多关系
     * @param map
     */
    void setSetmealAndTravelGroup(Map<String, Integer> map);

    /**
     * 分页回显
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 查询所有套餐游信息
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 用id查询套餐游信息
     * @param id
     * @return
     */
    Setmeal findById(Integer id);
}
