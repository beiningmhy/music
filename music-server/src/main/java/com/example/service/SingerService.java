package com.example.service;

import com.example.entity.Singer;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SingerService {
    List<Singer> findAll();

    PageInfo<Singer> findBySearch(Params params);

    void add(Singer singer);

    void update(Singer singer);

    void delete(Integer id);



    Singer findByById(Integer id);
}
