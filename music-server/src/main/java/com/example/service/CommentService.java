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
    Comment findById(Integer id);
    List<Comment> findByConsumerId();

    List<Comment> findBySongId2(Integer id);

    List<Comment> findBySongListId2(Integer id);

    List<Comment> findBySingerId2(Integer id);



}
