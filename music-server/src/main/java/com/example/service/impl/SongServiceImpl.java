package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.entity.ListSong;
import com.example.entity.Params;
import com.example.entity.Song;
import com.example.exception.CustomException;
import com.example.mapper.ListSongMapper;
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
    @Resource
    private ListSongMapper listSongMapper;

    @Override
    public List<Song> findAll(Params params) {
        return songMapper.findAll(params);
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
    public void add(Song song,Integer cont) {//cont=1表示同意导入，0表示判断一下是否存在
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (song.getName() == null || "".equals(song.getName())) {
            throw new CustomException("歌曲名不能为空");
        }
        if(song.getSingerId() == null||song.getSingerId()==0){
             throw new CustomException("歌手不能为空");
        }
        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        if(cont==0){
            Song user = songMapper.findByName(song.getName());
            if (user != null) {
                // 说明已经有了，这里我们就要提示前台不允许新增了
                throw new CustomException("该歌曲已存在，请勿重复添加");
            }
        }

        String time = DateUtil.now();
        song.setCreateTime(time);
        song.setUpdateTime(time);
        songMapper.insertSelective(song);
    }

    @Override
    public void update(Song song) {
        song.setUpdateTime(DateUtil.now());
        songMapper.updateByPrimaryKeySelective(song);
    }

    @Override
    public void delete(Integer id) {
        ListSong listSong = listSongMapper.findBySongId(id);
        if (listSong!=null){
            throw new CustomException("请先在歌单中移除该歌曲");
        }
        songMapper.deleteByPrimaryKey(id);
    }




    @Override
    public Song findByById(Integer id) {
        return songMapper.findById(id);
    }
}
