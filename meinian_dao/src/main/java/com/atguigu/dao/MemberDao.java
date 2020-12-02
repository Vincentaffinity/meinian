package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {

    /**
     * 根据手机号查询是否是会员
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
