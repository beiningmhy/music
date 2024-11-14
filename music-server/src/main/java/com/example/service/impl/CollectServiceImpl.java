package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Collect;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.CollectMapper;
import com.example.service.CollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectMapper collectMapper;

    @Override
    public List<Collect> findAll() {
        return collectMapper.selectAll();
    }

    @Override
    public PageInfo<Collect> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Collect> list = collectMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Collect collect) {
        Collect collect1 = null;
        if (collect.getSongId() != null) {
            collect1 = collectMapper.finBySongId(collect.getSongId(), collect.getUserId());
        }
        if (collect.getSongListId() != null) {
            collect1 = collectMapper.finBySongListId(collect.getSongListId(), collect.getUserId());
        }
        if (collect1 != null) {
            throw new CustomException("该歌曲或歌单已被收藏");
        }
        String time = DateUtil.now();

        collect.setCreateTime(time);


        collectMapper.insertSelective(collect);
    }

    @Override
    public void update(Collect collect) {


        collectMapper.updateByPrimaryKeySelective(collect);
    }

    @Override
    public void delete(Integer id) {
        collectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Collect> findBySongId(Integer id) {
        return collectMapper.findBySongId(id);
    }

    @Override
    public List<Collect> findBySongListId(Integer id) {
        return collectMapper.findBySongListId(id);
    }

    @Override
    public List<Collect> findByConsumerId() {
        return collectMapper.findByConsumerId();
    }


    @Override
    public Collect findById(Integer id) {
        return collectMapper.selectByPrimaryKey(id);
    }


}
