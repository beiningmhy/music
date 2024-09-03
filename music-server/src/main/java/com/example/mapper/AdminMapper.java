package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.Params;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Id;
import java.util.List;

@Repository
public interface AdminMapper extends Mapper<Admin> {
    List<Admin> findBySearch(@Param("params") Params params);
    @Select("select * from music_admin where name = #{name} limit 1")
    @ResultMap("AdminRM")
    Admin findByName(@Param("name") String name);

    @Select("select * from music_admin where name = #{name} and password = #{password} limit 1")
    @ResultMap("AdminRM")
    Admin findByNameAndPassword(@Param("name") String name, @Param("password") String password);
}
