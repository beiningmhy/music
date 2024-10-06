package com.example.service.impl;

import com.example.entity.Params;
import com.example.entity.SongList;
import com.example.exception.CustomException;
import com.example.mapper.SongListMapper;
import com.example.service.SongListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {
    @Resource
    private SongListMapper songListMapper;

    @Override
    public List<SongList> findAll() {
        return songListMapper.selectAll();
    }

    @Override
    public PageInfo<SongList> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<SongList> list = songListMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(SongList songList) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (songList.getTitle() == null || "".equals(songList.getTitle())) {
            throw new CustomException("歌单名不能为空");
        }
        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        SongList user = songListMapper.findByTitle(songList.getTitle());
        if (user != null) {
            // 说明已经有了，这里我们就要提示前台不允许新增了
            throw new CustomException("该歌单已存在，请勿重复添加");
        }



        songListMapper.insertSelective(songList);
    }

    @Override
    public void update(SongList songList) {
        songListMapper.updateByPrimaryKeySelective(songList);
    }

    @Override
    public void delete(Integer id) {
        songListMapper.deleteByPrimaryKey(id);
    }




    @Override
    public SongList findByById(Integer id) {
        return songListMapper.selectByPrimaryKey(id);
    }

    @Override
    public SongList findByName(String name) {
        return songListMapper.findByTitle(name);
    }

    @Override
    public List<String> findAllStyle() {
        return songListMapper.findAllStyle();
    }

    @Override
    public List<SongList> findBySongId(Integer id) {
        return songListMapper.findBySongId(id);
    }
}
