package com.example.service.impl;

import com.example.entity.Params;
import com.example.entity.Song;
import com.example.exception.CustomException;
import com.example.mapper.SongMapper;
import com.example.service.SongService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    @Resource
    private SongMapper songMapper;

    @Override
    public List<Song> findAll() {
        return songMapper.selectAll();
    }

    @Override
    public PageInfo<Song> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Song> list = songMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Song song) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (song.getName() == null || "".equals(song.getName())) {
            throw new CustomException("用户名不能为空");
        }
        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        Song user = songMapper.findByName(song.getName());
        if (user != null) {
            // 说明已经有了，这里我们就要提示前台不允许新增了
            throw new CustomException("该歌手已存在，请勿重复添加");
        }



        songMapper.insertSelective(song);
    }

    @Override
    public void update(Song song) {
        songMapper.updateByPrimaryKeySelective(song);
    }

    @Override
    public void delete(Integer id) {
        songMapper.deleteByPrimaryKey(id);
    }




    @Override
    public Song findByById(Integer id) {
        return songMapper.selectByPrimaryKey(id);
    }
}
