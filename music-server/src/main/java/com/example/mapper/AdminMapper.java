package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin> findBySearch(@Param("params") Params params);
    @Select("select * from admin where name = #{name} limit 1")
    Admin findByName(@Param("name") String name);

    @Select("select * from admin where name = #{name} and password = #{password} limit 1")
    Admin findByNameAndPassword(@Param("name") String name, @Param("password") String password);
}
