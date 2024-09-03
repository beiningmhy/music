package com.example.service;

import com.example.entity.Consumer;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ConsumerService {
    List<Consumer> findAll();

    PageInfo<Consumer> findBySearch(Params params);

    void add(Consumer consumer);

    void update(Consumer consumer);

    void delete(Integer id);

    Consumer login(Consumer consumer);

    Consumer findByById(Integer id);
}
