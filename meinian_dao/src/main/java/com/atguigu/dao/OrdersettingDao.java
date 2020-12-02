package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrdersettingDao {

    /**
     * 查看是否存在原信息
     * @param orderDate
     * @return
     */
    long findCountByOrderDate(Date orderDate);

    /**
     * 修改预约日期信息
     * @param orderSetting
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 插入新数据
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 按月查询预约信息
     * @param map
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(Map<String, Object> map);

    /**
     * 以预约时间查询是否有预约记录
     * @param date
     * @return
     */
    OrderSetting findByOrderDate(Date date);

    /**
     * 更新预约信息表
     * @param orderSetting
     */
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
