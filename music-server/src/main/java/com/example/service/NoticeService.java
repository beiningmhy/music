package com.example.service;

import com.example.entity.Notice;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoticeService {
    void add(Notice notice);
    PageInfo<Notice> findBySearch(Params params);

    void update(Notice notice);

    void delete(Integer id);

    List<Notice> findTop();
    List<Notice> findTopById(Integer id);
}
