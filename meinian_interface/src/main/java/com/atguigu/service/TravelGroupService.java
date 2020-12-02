package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

public interface TravelGroupService {

    /**
     * 增加跟团游数据
     * @param travelGroup
     * @param travelItemIds
     */
    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    /**
     * 按照条件分页查询跟团游数据
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id查询跟团游信息
     * @param id
     * @return
     */
    TravelGroup findById(Integer id);

    /**
     * 中间表查询 为了实现勾选框回显
     * @param id
     * @return
     */
    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    /**
     * 编辑团队游信息
     * @param travelGroup
     * @param travelItemIds
     */
    void edit(TravelGroup travelGroup, Integer[] travelItemIds);

    /**
     * 删除跟团游
     * @param id
     */
    void deleteGroupInf(Integer id);

    /**
     * 查询跟团游信息
     * @return
     */
    List<TravelGroup> findAll();
}
