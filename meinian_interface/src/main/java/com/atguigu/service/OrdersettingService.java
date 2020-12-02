package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrdersettingService {

    /**
     * 上传预约信息
     * @param orderSettings
     */
    void add(List<OrderSetting> orderSettings);

    /**
     * 获取预约信息进行回显
     * @param date
     * @return
     */
    List<Map> getOrderSettingByMonth(String date);

    /**
     * 设置预约信息
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
