package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService{

    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    /**
     * 个人游分页显示
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 1：初始化分页操作
        PageHelper.startPage(currentPage,pageSize);
        // 2：使用sql语句进行查询（不必在使用mysql的limit了）
        Page<TravelItem> page = travelItemDao.findPage(queryString);
        // 3：封装
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除个人游
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //查看是否关联表中存在
        long count =  travelItemDao.findCountByTravelItemId(id);
        if(count > 0){
            throw new RuntimeException("不允许删除");
        }
        travelItemDao.deleteById(id);
    }

    /**
     * 以id获取单人游信息
     * @param id
     * @return
     */
    @Override
    public TravelItem findById(Integer id) {
        TravelItem travelItem = travelItemDao.findById(id);
        return travelItem;
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    /**
     * 获取所有个人游列表信息
     * @return
     */
    @Override
    public List<TravelItem> findAdd() {
        List<TravelItem> list = travelItemDao.findAll();
        return list;
    }

}
