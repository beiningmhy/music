package com.example.service;

import com.example.entity.Params;
import com.example.entity.SongList;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SongListService {
    List<SongList> findAll();

    PageInfo<SongList> findBySearch(Params params);

    void add(SongList songList);

    void update(SongList songList);

    void delete(Integer id);

    SongList findByById(Integer id);
    SongList findByName(String name);
    List<String> findAllStyle();
    List<SongList> findBySongId(Integer id);
}
