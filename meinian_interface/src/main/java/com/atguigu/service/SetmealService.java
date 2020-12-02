package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    /**
     * 增加套餐游信息
     * @param travelGroupIds
     * @param setmeal
     */
    void add(Integer[] travelGroupIds, Setmeal setmeal);

    /**
     * 分页功能实现
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 查询所有套餐游信息
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 用id查询响应套餐信息
     * @param id
     * @return
     */
    Setmeal findById(Integer id);
}
