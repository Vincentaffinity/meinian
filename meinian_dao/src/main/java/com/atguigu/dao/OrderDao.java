package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    /**
     * 根据信息查询是否已经预约
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 向预约表中新插入一个数据
     * @param order
     */
    void add(Order order);

    /**
     * 根据id查询order细节信息进行回显
     * @param id
     * @return
     */
    Map findById4Detail(Integer id);
}
