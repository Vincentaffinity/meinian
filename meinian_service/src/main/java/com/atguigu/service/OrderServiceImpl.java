package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.pojo.Member;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 设置预约信息
     *
     * 1. 判断当前的日期是否可以预约(根据orderDate查询t_ordersetting, 能查询出来可以预约;查询不出来,不能预约)
     * 2. 判断当前日期是否预约已满(判断reservations（已经预约人数）是否等于number（最多预约人数）)
     * 3. 判断是否是会员(根据手机号码查询t_member)
     *   - 如果是会员(能够查询出来), 防止重复预约(根据member_id,orderDate,setmeal_id查询t_order)
     *   - 如果不是会员(不能够查询出来),自动注册为会员(直接向t_member插入一条记录)
     * 4.进行预约
     *    - 向t_order表插入一条记录
     *    - t_ordersetting表里面预约的人数reservations+1
     *
     * @param map
     * @return
     */
    @Override
    public Result order(Map map) throws Exception {
        //检查当前日期是否进行了预约设置
        String orderDate = (String) map.get("orderDate");
        // 因为数据库预约设置表里面的时间是date类型，http协议传递的是字符串类型，所以需要转换
        Date date = DateUtils.parseString2Date(orderDate);
        // 使用预约时间查询预约设置表，看看是否可以 进行预约
        //（1）使用预约时间，查询预约设置表，判断是否有该记录
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        // 如果预约设置表等于null，说明不能进行预约，压根就没有开团
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else {
            //如果有，说明预约可以进行预约，//可预约人数
            int number = orderSetting.getNumber();
            //已预约人数
            int reservations = orderSetting.getReservations();
            //如果预约人数大于等于最大预约数，此时不能预约，提示“预约已满”
            if (reservations >= number) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }

        //获取手机号
        String telephone = (String) map.get("telephone");
        //（2）使用手机号，查询会员表，判断当前预约人是否是会员
        // 根据手机号，查询会员表，判断当前预约人是否是会员
        Member member = memberDao.findByTelephone(telephone);
        //如果是会员，防止重复预约（一个会员、一个时间、一个套餐不能重复，否则是重复预约）
        if (member != null) {
            Integer memberId = member.getId();
            // 获取套餐id
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(memberId, date, null, null, setmealId);
            // 根据预约信息查询是否已经预约
            List<Order> list = orderDao.findByCondition(order);
            // 判断是否已经预约list不等于null，说明已经预约，不能重复预约
            if (list != null && list.size() > 0) {
                //已经完成了预约，不能重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else{
            // 如果不是会员：注册会员，向会员表中添加数据
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setPhoneNumber((String)map.get("telephone"));
            member.setIdCard((String)map.get("idCard"));
            member.setRegTime(new Date()); // 会员注册时间，当前时间
            memberDao.add(member);
        }
        //（3）可以进行预约，更新预约设置表中预约人数的值，使其的值+1
        //可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        //（4）可以进行预约，向预约表中添加1条数据
        //保存预约信息到预约表
        Order order = new Order();
        order.setMemberId(member.getId()); //会员id
        order.setOrderDate(date); // 预约时间
        order.setOrderStatus(Order.ORDERSTATUS_NO); // 预约状态（已到诊/未到诊）
        order.setOrderType((String)map.get("orderType"));
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderDao.add(order);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    /**
     * 查询旅游细节信息 进行回显
     * @param id
     * @return
     */
    @Override
    public Map findById4Detail(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
            return map;
        }
        return map;
    }
}
