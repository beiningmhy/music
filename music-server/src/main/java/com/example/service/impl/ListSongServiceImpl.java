package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.JwtTokenUtils;
import com.example.entity.ListSong;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.ListSongMapper;
import com.example.service.ListSongService;
import com.example.service.ListSongService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {
    @Resource
    private ListSongMapper listSongMapper;



    @Override
    public void add(ListSong listSong) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (listSong.getSongId() == null|| listSong.getSongListId() == null) {
            throw new CustomException("歌曲和歌单不能为空");
        }
        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        ListSong user = listSongMapper.findBySidAndSLid(listSong.getSongId(), listSong.getSongListId());
        if (user != null) {
            // 说明已经有了，这里我们就要提示前台不允许新增了
            throw new CustomException("该歌曲已经添加至该歌单内，请重新添加！");
        }

        listSongMapper.insertSelective(listSong);
    }

    @Override
    public void update(ListSong listSong) {

        listSongMapper.updateByPrimaryKeySelective(listSong);
    }

    @Override
    public void delete(Integer songId ,Integer songListId) {
        ListSong listSong = listSongMapper.findBySidAndSLid(songId, songListId);
        listSongMapper.deleteByPrimaryKey(listSong.getId());
    }

    @Override
    public ListSong findById(Integer id) {
        return listSongMapper.selectByPrimaryKey(id);
    }


}
