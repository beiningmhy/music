package com.example.service;

import com.example.entity.Log;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

public interface LogService {
    void add(Log log);

    void delete(Integer id);





    PageInfo<Log> findBySearch(Params params);
}
