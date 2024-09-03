package com.example.mapper;

import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.entity.Singer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SingerMapper extends Mapper<Singer> {
    List<Singer> findBySearch(@Param("params") Params params);
    @Select("select * from music_singer where name = #{name} limit 1")
    @ResultMap("SingerRM")
    Singer findByName(@Param("name") String name);


}
