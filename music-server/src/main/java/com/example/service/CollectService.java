package com.example.service;

import com.example.entity.Collect;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CollectService {
    List<Collect> findAll();

    PageInfo<Collect> findBySearch(Params params);

    void add(Collect collect);

    void update(Collect collect);

    void delete(Integer id);

    List<Collect> findBySongId();

    List<Collect> findBySongListId();
    List<Collect> findByConsumerId();

    Collect findById(Integer id);





}