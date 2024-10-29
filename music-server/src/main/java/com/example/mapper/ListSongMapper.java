package com.example.mapper;

import com.example.entity.ListSong;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ListSongMapper extends Mapper<ListSong> {

    @Select("select * from music_list_song where song_id = #{songId} and song_list_id = #{songListId} limit 1")
    @ResultMap("ListSongRM")
    ListSong findBySidAndSLid(@Param("songId") Integer songId,@Param("songListId") Integer songListId);

    @Select("select * from music_list_song where song_id = #{songId} limit 1")
    ListSong findBySongId(Integer songId);

    @Select("select * from music_list_song where song_list_id = #{songListId}")
    ListSong findBySongListId(Integer songListId);

}
