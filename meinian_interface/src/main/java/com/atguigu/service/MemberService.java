package com.atguigu.service;

import com.atguigu.pojo.Member;

public interface MemberService {

    /**
     * 根据电话号查询客户
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 增加会员信息
     * @param member
     */
    void add(Member member);
}
