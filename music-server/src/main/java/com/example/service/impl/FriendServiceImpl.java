package com.example.service.impl;

import cn.hutool.core.lang.Dict;
import com.example.entity.Friend;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.FriendMapper;
import com.example.service.FriendService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {
    @Resource
    private FriendMapper friendMapper;

    @Override
    public List<Friend> findAll() {
        return friendMapper.selectAll();
    }

    @Override
    public PageInfo<Friend> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Friend> list = friendMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Friend friend) {
        List<Friend> list = friendMapper.findByFriend1AndFriend2(friend.getFriend1(), friend.getFriend2());
        if (list.size() > 0) {
            throw new CustomException("该好友已存在");
        }

        friendMapper.insertSelective(friend);
    }

    @Override
    public void update(Friend friend) {

        friendMapper.updateByPrimaryKeySelective(friend);
    }

    @Override
    public void delete(Integer id) {
        friendMapper.deleteByPrimaryKey(id);
    }




    @Override
    public Friend findByById(Integer id) {
        return friendMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Friend> findByFriend1orFriend2(String user) {
        return friendMapper.findByFriend1orFriend2(user);
    }


}
