package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;


public interface TravelGroupDao {

    /**
     * 往跟团游表加入跟团游数据
     * @param travelGroup
     */
    void add(TravelGroup travelGroup);

    /**
     * 中间表加入数据
     * @param map
     */
    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    /**
     * 根据条件分页查询跟团游数据
     * @param queryString
     * @return
     */
    Page<TravelGroup> findPage(String queryString);

    /**
     * 根据id查找跟团游信息并进行回显
     * @param id
     * @return
     */
    TravelGroup findById(Integer id);

    /**
     * 中间表查询 为回显操作
     * @param id
     * @return
     */
    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    /**
     * 修改中间表数据
     * @param travelGroup
     */
    void edit(TravelGroup travelGroup);

    /**
     * 删除中间表原信息
     * @param id
     */
    void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id);

    /**
     * 删除跟团表信息
     * @param id
     */
    void deleteGroupInf(Integer id);

    /**
     * 查询所有跟团游信息
     * @return
     */
    List<TravelGroup> findAll();

    /**
     * 根据id查询跟团游信息
     * @param id
     * @return
     */
    List<TravelGroup> findTravelGroupListById(Integer id);
}
