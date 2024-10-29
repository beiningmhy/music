package com.example.service;

import com.example.entity.ListSong;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ListSongService {

    void add(ListSong listSong);

    void update(ListSong listSong);

    void delete(Integer songId, Integer songListId);
    ListSong findById(Integer id);
    ListSong findBySongListId(Integer songListId);


}
