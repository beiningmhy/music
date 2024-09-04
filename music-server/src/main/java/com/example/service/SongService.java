package com.example.service;

import com.example.entity.Params;
import com.example.entity.Song;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SongService {
    List<Song> findAll();

    PageInfo<Song> findBySearch(Params params);



    void add(Song song, Integer cont);

    void update(Song song);

    void delete(Integer id);



    Song findByById(Integer id);

}
