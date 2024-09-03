package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.Consumer;
import com.example.entity.Params;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ConsumerMapper extends Mapper<Consumer> {
    List<Consumer> findBySearch(@Param("params") Params params);
    @Select("select * from music_consumer where username = #{username} limit 1")
    @ResultMap("ConsumerRM")
    Consumer findByUsername(@Param("username") String username);

    @Select("select * from music_consumer where username = #{username} and password = #{password} limit 1")
    @ResultMap("ConsumerRM")
    Consumer findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}