package com.example.mapper;

import com.example.entity.Params;
import com.example.entity.Song;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SongMapper extends Mapper<Song> {
    List<Song> findBySearch(@Param("params") Params params);

    @Select("select * from music_song where name = #{name} limit 1")
    @ResultMap("SongRM")
    Song findByName(@Param("name") String name);

    @Select("select music_song.*,music_singer.name as singerName from music_song  left join music_singer   " +
            "on music_song.singer_id = music_singer.id where music_song.id=#{id}")
    @ResultMap("SongRM")
    Song findById(@Param("id") Integer id);


}
