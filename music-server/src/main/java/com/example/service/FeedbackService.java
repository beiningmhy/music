package com.example.service;

import com.example.entity.Feedback;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface FeedbackService {
    List<Feedback> findAll();

    PageInfo<Feedback> findBySearch(Params params);

    void add(Feedback feedback);

    void update(Feedback feedback);

    void delete(Integer id);


    Feedback findByById(Integer id);

}
