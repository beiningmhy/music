package com.example.service;

import cn.hutool.core.lang.Dict;
import com.example.entity.Imsingle;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImsingleService {


    List<Imsingle>  findByUsername(String fromUser,String toUser);
    Integer findUnReadNums(String fromUser,String touser);
    List<Imsingle> findAll();

    PageInfo<Imsingle> findBySearch(Params params);

    void add(Imsingle imsingle);

    void update(Imsingle imsingle);

    void delete(Integer id);



    Imsingle findByById(Integer id);

}
