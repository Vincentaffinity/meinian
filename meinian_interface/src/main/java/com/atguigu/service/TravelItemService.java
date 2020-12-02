package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemService {

    /**
     * 插入信息
     * @param travelItem
     */
    public void add(TravelItem travelItem);

    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    public void deleteById(Integer id);

    public TravelItem findById(Integer id);

    /**
     * 编辑个人游信息
     * @param travelItem
     */
    public void edit(TravelItem travelItem);

    /**
     * 查询所有个人游信息
     * @return
     */
    public List<TravelItem> findAdd();
}
