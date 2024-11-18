package com.example.mapper;



import com.example.entity.Notice;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NoticeMapper extends Mapper<Notice> {

    List<Notice> findBySearch(@Param("params") Params params);

    @Select("select * from notice order by time desc limit 3")
    List<Notice> findTop3();
    @Select("select * from music_notice where status=0 order by time desc limit  #{id}")
    List<Notice> findTopById(@Param("id") Integer id);
}