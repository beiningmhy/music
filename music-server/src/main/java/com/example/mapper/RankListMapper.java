package com.example.mapper;

import com.example.entity.RankList;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RankListMapper extends Mapper<RankList> {
    List<RankList> findBySearch(@Param("params") Params params);



    @Select("select music_song_list.title as songListTitle,music_song_list.id from music_song_list where id in (SELECT DISTINCT song_list_id FROM music_rank_list WHERE song_list_id IS NOT NULL ORDER BY song_list_id ASC)")
    List<RankList> findBySongListId();

    @Select("select music_consumer.username as username,music_consumer.id from music_consumer where id in (SELECT DISTINCT consumer_id FROM music_rank_list WHERE consumer_id IS NOT NULL ORDER BY consumer_id ASC)")
    List<RankList> findByConsumerId();

}
