package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelgroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    /**
     * 查询所有跟团游信息
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        List<TravelGroup> list = travelGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS, list);
    }

    /**
     * 删除跟团游信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteGroupInf")
    public Result deleteGroupInf(Integer id){
        try{
            travelGroupService.deleteGroupInf(id);
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
    }

    /**
     * 编辑修改团队游数据
     * @param travelGroup
     * @param travelItemIds
     * @return
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        try{
            travelGroupService.edit(travelGroup, travelItemIds);
        }catch (Exception e){
            return new Result(false, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    /**
     * id查找中间表 实现回显功能
     * @param id
     * @return
     */
    @RequestMapping("/findTravelItemIdByTravelGroupId")
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id){
        List<Integer> list = travelGroupService.findTravelItemIdByTravelGroupId(id);
        return list;
    }

    /**
     * 以id查询跟团游数据
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelGroup travelGroup = null;
        try{
            travelGroup = travelGroupService.findById(id);
        }catch(Exception e){
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelGroup);
    }

    /**
     * 按照条件查询分页信息
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult =  travelGroupService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }


    /**
     * 增加新跟团游数据
     * @param travelGroup
     * @param travelItemIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        try{
            travelGroupService.add(travelGroup, travelItemIds);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }

}
