package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    /**
     * 查询所有个人游信息
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        List<TravelItem> list = null;
        try{
            list = travelItemService.findAdd();
        }catch (Exception e){
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, list);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelItem travelItem){
        try{
            travelItemService.edit(travelItem);
        }catch (Exception e){
            return new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    /**
     * 找自由行信息 为了回显在页面上
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelItem travelItem = null;
        try{
            travelItem = travelItemService.findById(id);
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS, travelItem);
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            travelItemService.deleteById(id);
        }catch (RuntimeException e){
            // 运行时异常，表示自由行和跟团游的关联表中存在数据
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelItemService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TravelItem travelItem){
        try{
            travelItemService.add(travelItem);
        }catch (Exception e) {
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }
}
