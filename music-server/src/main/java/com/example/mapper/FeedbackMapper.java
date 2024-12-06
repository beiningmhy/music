package com.example.mapper;

import com.example.entity.Feedback;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FeedbackMapper extends Mapper<Feedback> {
    List<Feedback> findBySearch(@Param("params") Params params);


}
