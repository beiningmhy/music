package com.example.service;

import com.example.entity.Comment;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    PageInfo<Comment> findBySearch(Params params);

    void add(Comment comment);

    void update(Comment comment);

    void delete(Integer id);

    List<Comment> findBySongId();

    List<Comment> findBySongListId();

    List<Comment> findBySingerId();





}
