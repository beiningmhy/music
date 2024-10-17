package com.example.service;

import com.example.entity.RankList;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RankListService {
    List<RankList> findAll();

    PageInfo<RankList> findBySearch(Params params);

    void add(RankList rankList);

    void update(RankList rankList);

    void delete(Integer id);


    List<RankList> findBySongListId();


    RankList findById(Integer id);
    List<RankList> findByConsumerId();





}
