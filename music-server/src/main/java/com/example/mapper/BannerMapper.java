package com.example.mapper;

import com.example.entity.Banner;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BannerMapper extends Mapper<Banner> {
    List<Banner> findBySearch(@Param("params") Params params);


    @Select("select * from music_banner where status = 0")
    List<Banner> findAll();
}
