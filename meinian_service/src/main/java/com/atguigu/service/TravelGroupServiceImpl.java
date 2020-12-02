package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService{

    @Autowired
    private TravelGroupDao travelGroupDao;

    /**
     * 增加跟团游数据
     * @param travelGroup
     * @param travelItemIds
     */
    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        // 1 新增跟团游，想t_travelgroup中添加数据，新增后返回新增的id
        travelGroupDao.add(travelGroup);
        // 2 新增跟团游和自由行中间表t_travelgroup_travelitem新增数据(新增几条，由travelItemIds决定)
        setTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);
    }

    /**
     * 按照条件分页查询跟团游数据
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 使用分页插件PageHelper，设置当前页，每页最多显示的记录数
        PageHelper.startPage(currentPage, pageSize);

        Page<TravelGroup> page = travelGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询跟团游信息并进行回显
     * @param id
     * @return
     */
    @Override
    public TravelGroup findById(Integer id) {
        TravelGroup travelGroup = travelGroupDao.findById(id);
        return travelGroup;
    }

    /**
     * 勾选框回显 中间表查询
     * @param id
     * @return
     */
    @Override
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id) {
        List<Integer> list = travelGroupDao.findTravelItemIdByTravelGroupId(id);
        return list;
    }

    /**
     * 编辑团队游数据
     * @param travelGroup
     * @param travelItemIds
     */
    @Override
    public void edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        // 修改团队游表信息
        travelGroupDao.edit(travelGroup);
        // 删除原中间表信息
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroup.getId());
        // 加入新中间表数据
        this.setTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);
    }

    /**
     * 删除报团游信息
     * @param id
     */
    @Override
    public void deleteGroupInf(Integer id) {
        //删除中间表信息
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(id);
        travelGroupDao.deleteGroupInf(id);
    }

    /**
     * 查询所有跟团游信息
     * @return
     */
    @Override
    public List<TravelGroup> findAll() {
        List<TravelGroup> list = travelGroupDao.findAll();
        return list;
    }

    /**
     * 中间表加入数据处理
     * @param groupId
     * @param travelItemIds
     */
    public void setTravelGroupAndTravelItem(Integer groupId, Integer[] travelItemIds){
        if (travelItemIds != null && travelItemIds.length > 0){
            for(Integer travelItemId: travelItemIds){
                Map<String, Integer> map = new HashMap<>();
                map.put("groupId", groupId);
                map.put("travelItemId", travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }
    }
}
