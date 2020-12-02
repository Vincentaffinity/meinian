package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;


public interface TravelItemDao {

    /**
     * 增加个人游游信息
     * @param travelItem
     */
    void add(TravelItem travelItem);

    /**
     * 获取分页个人游信息
     * @param queryString
     * @return
     */
    Page<TravelItem> findPage(String queryString);

    /**
     * 查看要删除的在中间表有几条
     * @param id
     * @return
     */
    long findCountByTravelItemId(Integer id);

    /**
     * 删除个人游信息
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 以id查询个人游信息
     * @param id
     * @return
     */
    TravelItem findById(Integer id);

    /**
     * 编辑个人游信息
     * @param travelItem
     */
    void edit(TravelItem travelItem);

    /**
     * 查询所有个人游信息
     * @return
     */
    List<TravelItem> findAll();

    /**
     * 中间表查询对应套餐游关联信息
     * @param id
     * @return
     */
    List<TravelItem> findTravelItemListById(Integer id);
}
