package com.example.service;

import com.example.entity.Log;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface LogService {
    void add(Log log);

    void delete(Integer id);

    PageInfo<Log> findBySearch(Params params);
    List<Log>findByUserId();
}
