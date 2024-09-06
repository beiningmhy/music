package com.example.mapper;

import com.example.entity.Address;
import com.example.entity.Params;
import com.example.entity.Song;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AddressMapper extends Mapper<Address> {
//    List<Song> findBySearch(@Param("params") Params params);

    @Select("select * from music_address where name = #{name} limit 1")
    @ResultMap("AddressRM")
    Address findByName(@Param("name") String name);


    List<Address> findByParentId(@Param("params") Params params);

    List<Address> findById(@Param("params") Params params);


}
