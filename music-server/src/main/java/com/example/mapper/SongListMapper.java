package com.example.mapper;

import com.example.entity.Params;
import com.example.entity.SongList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SongListMapper extends Mapper<SongList> {
    List<SongList> findBySearch(@Param("params") Params params);
    @Select("select * from music_song_list where title like #{ title } limit 1")
    @ResultMap("SongListRM")
    SongList findByTitle(@Param("title") String title);
    @Select("select distinct style from music_song_list")
    List<String> findAllStyle();

    @Select("select * from music_song_list where id in (select song_list_id from music_list_song where song_id = #{ id })")
    List<SongList> findBySongId(Integer id);


}
