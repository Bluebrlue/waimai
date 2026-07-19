package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据 openid 查询用户
     *
     * @param openid 微信用户唯一标识
     * @return 用户对象，未找到返回 null
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 新增用户（仅 openid 和 createTime 两个字段）
     *
     * @param user 用户信息
     */
    @Insert("insert into user (openid, create_time) values (#{openid}, #{createTime})")
    void insert(User user);
}
