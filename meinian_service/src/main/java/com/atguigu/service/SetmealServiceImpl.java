package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 增加套餐游信息
     * @param travelGroupIds
     * @param setmeal
     */
    @Override
    public void add(Integer[] travelGroupIds, Setmeal setmeal) {
        setmealDao.add(setmeal);
        // 向套餐和跟团游的中间表中插入数据
        if(travelGroupIds != null && travelGroupIds.length > 0){
            // 绑定套餐和跟团游的多对多关系
            setSetmealAndTravelGroup(setmeal.getId(), travelGroupIds);
        }
        //保存图片到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    /**
     * 分页信息回显
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 查询所有套餐游信息
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> list = setmealDao.findAll();
        return list;
    }

    /**
     * 用id查询套餐游信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    /**
     * 绑定套餐和跟团游的多对多关系
     * @param id
     * @param travelGroupIds
     */
    public void setSetmealAndTravelGroup(Integer id, Integer[] travelGroupIds){
        if(travelGroupIds != null && travelGroupIds.length != 0){
            for(int i = 0; i < travelGroupIds.length; i++){
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("setmeal_id", id);
                map.put("travelgroup_id", travelGroupIds[i]);
                setmealDao.setSetmealAndTravelGroup(map);
            }
        }
    }
}
