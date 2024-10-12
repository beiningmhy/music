package com.example.mapper;

import com.example.entity.Comment;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentMapper extends Mapper<Comment> {
    List<Comment> findBySearch(@Param("params") Params params);

    @Select("select music_song.name as songName,music_song.id from music_song where id in (SELECT DISTINCT song_id FROM music_comment WHERE song_id IS NOT NULL ORDER BY song_id ASC)")
    List<Comment> findBySongId();

    @Select("select music_singer.name as singerName,music_singer.id from music_singer where id in (SELECT DISTINCT singer_id FROM music_comment WHERE singer_id IS NOT NULL ORDER BY singer_id ASC)")
    List<Comment> findBySingerId();

    @Select("select music_song_list.title as songListTitle,music_song_list.id from music_song_list where id in (SELECT DISTINCT song_list_id FROM music_comment WHERE song_list_id IS NOT NULL ORDER BY song_list_id ASC)")
    List<Comment> findBySongListId();

}
