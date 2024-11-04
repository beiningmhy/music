package com.example.service;

import com.example.entity.Banner;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BannerService {
    List<Banner> findAll();

    PageInfo<Banner> findBySearch(Params params);

    void add(Banner banner);

    void update(Banner banner);

    void delete(Integer id);

    Banner findByById(Integer id);

}
