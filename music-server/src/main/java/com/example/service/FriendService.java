package com.example.service;

import cn.hutool.core.lang.Dict;
import com.example.entity.Friend;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface FriendService {



    List<Friend> findAll();

    PageInfo<Friend> findBySearch(Params params);

    void add(Friend friend);

    void update(Friend friend);

    void delete(Integer id);



    Friend findByById(Integer id);

   List<Friend> findByFriend1orFriend2(String user);

}
