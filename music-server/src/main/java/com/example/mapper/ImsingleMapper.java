package com.example.mapper;

import com.example.entity.Friend;
import com.example.entity.Imsingle;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ImsingleMapper extends Mapper<Imsingle> {
    List<Imsingle> findBySearch(@Param("params") Params params);
    @Select("select * from music_imsingle where fromuser = #{fromUser} and touser = #{toUser}")
    List<Imsingle> findByUsername(@Param("fromUser") String fromUser, @Param("toUser") String toUser);
    @Select("select * from music_imsingle where readed = 0 and touser = #{toUsername}")
    List<Imsingle> findByToUsername(@Param("toUsername") String toUsername);

    @Select("select count(*) from music_imsingle where readed = 1 and fromuser=#{fromuser} and touser=#{touser}")
    Integer findUnReadNums(@Param("fromuser") String fromuser,@Param("touser") String touser);

    @Select("select * from music_imsingle where( fromuser=#{fromuser} and touser=#{touser} ) or( fromuser=#{touser} and touser=#{fromuser} ) order by time asc")
    List<Imsingle> findByFromuserAndTouser(@Param("fromuser") String fromuser, @Param("touser") String touser);

}
