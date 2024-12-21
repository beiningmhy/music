package com.example.mapper;

import com.example.entity.Friend;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FriendMapper extends Mapper<Friend> {
    List<Friend> findBySearch(@Param("params") Params params);
    @Select("select * from music_friend where( friend1=#{user1} and friend2=#{user2} ) or( friend2=#{user1} and friend1=#{user2} ) ")
    List<Friend> findByFriend1AndFriend2(@Param("user1") String user1, @Param("user2") String user2);
    @Select("select * from music_friend where friend1=#{user} or friend2=#{user}")
    List<Friend> findByFriend1orFriend2(@Param("user") String user);

}
