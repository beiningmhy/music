package com.example.mapper;

import com.example.entity.Collect;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CollectMapper extends Mapper<Collect> {
    List<Collect> findBySearch(@Param("params") Params params);

//    @Select("select music_song.name as songName,music_song.id from music_song where id in (SELECT DISTINCT song_id FROM music_collect WHERE song_id IS NOT NULL ORDER BY song_id ASC)")
    List<Collect> findBySongId(@Param("id") Integer id);


//    @Select("select music_song_list.title as songListTitle,music_song_list.id from music_song_list where id in (SELECT DISTINCT song_list_id FROM music_collect WHERE song_list_id IS NOT NULL ORDER BY song_list_id ASC)")
    List<Collect> findBySongListId(@Param("id") Integer id);

    @Select("select music_consumer.username as username,music_consumer.id from music_consumer where id in (SELECT DISTINCT user_id FROM music_collect WHERE user_id IS NOT NULL ORDER BY user_id ASC)")
    List<Collect> findByConsumerId();

    @Select("select * from music_collect where song_id=#{id} and user_id=#{userId} limit 1")
    Collect finBySongId(@Param("id") Integer id,@Param("userId")Integer userId);
    @Select("select * from music_collect where song_list_id=#{id} and user_id=#{userId} limit 1")
    Collect finBySongListId(@Param("id") Integer id,@Param("userId")Integer userId);
    @Select("select * from music_collect where user_id=#{userId} and status= 0")
    @ResultMap("CollectRM")
    List<Collect> findByUserId(@Param("userId")Long userId);
}
