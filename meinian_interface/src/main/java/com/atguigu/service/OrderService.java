package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Map;

public interface OrderService {

    /**
     * 设置预约信息
     * @param map
     * @return
     */
    Result order(Map map) throws Exception;

    /**
     * 查询旅游细节信息 进行回显
     * @param id
     * @return
     */
    Map findById4Detail(Integer id) throws Exception;
}
