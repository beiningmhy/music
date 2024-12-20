package com.example.mapper;

import com.example.entity.Log;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface LogMapper extends Mapper<Log> {

    List<Log> findBySearch(@Param("params") Params params);
    @Select("select DISTINCT username from music_log ")
    List<Log> findByUserId();
}
