package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    /**
     * 增添或修改预约信息
     * @param orderSettings
     */
    @Override
    public void add(List<OrderSetting> orderSettings) {
        // 1：遍历List<OrderSetting>
        for (OrderSetting orderSetting : orderSettings) {
            // 判断当前的日期之前是否已经被设置过预约日期，使用当前时间作为条件查询数量
            long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            // 如果设置过预约日期，更新number数量
            if (count > 0){
                ordersettingDao.editNumberByOrderDate(orderSetting);
            }else {
                // 如果没有设置过预约日期，执行保存
                ordersettingDao.add(orderSetting);
            }
        }
    }

    /**
     * 预约数据回显
     * @param date
     * @return
     */
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";
        Map<String, Object> map = new HashMap<>();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list = ordersettingDao.getOrderSettingByMonth(map);
        List<Map> outList = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String, Integer> mapForPut = new HashMap<>();
            mapForPut.put("date", orderSetting.getOrderDate().getDate());
            mapForPut.put("number", orderSetting.getNumber());
            mapForPut.put("reservations", orderSetting.getReservations());
            outList.add(mapForPut);
        }
        return outList;
    }

    /**
     * 设置预约信息
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            ordersettingDao.editNumberByOrderDate(orderSetting);
        }else{
            // 如果没有设置过预约日期，执行保存
            ordersettingDao.add(orderSetting);
        }
    }

}
