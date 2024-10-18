package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.entity.RankList;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.RankListMapper;
import com.example.service.RankListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RankListServiceImpl implements RankListService {
    @Resource
    private RankListMapper rankListMapper;

    @Override
    public List<RankList> findAll() {
        return rankListMapper.selectAll();
    }

    @Override
    public PageInfo<RankList> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<RankList> list = rankListMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(RankList rankList) {


        rankListMapper.insertSelective(rankList);
    }

    @Override
    public void update(RankList rankList) {


        rankListMapper.updateByPrimaryKeySelective(rankList);
    }

    @Override
    public void delete(Integer id) {
        rankListMapper.deleteByPrimaryKey(id);
    }



    @Override
    public List<RankList> findBySongListId() {
        return rankListMapper.findBySongListId();
    }



    @Override
    public RankList findById(Integer id) {
        return rankListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RankList> findByConsumerId() {
        return rankListMapper.findByConsumerId();
    }

    @Override
    public List<RankList> avgForRankList(Params params) {
        return rankListMapper.findBySearch(params);
    }


}
