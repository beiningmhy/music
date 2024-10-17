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


        String time = DateUtil.now();

        collect.setCreateTime(time);
        if (collect.getStatus()==null){
            collect.setStatus("1");//注册先置为禁用，登录后才为启用
        }

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
    public List<Collect> findBySongId() {
        return collectMapper.findBySongId();
    }

    @Override
    public List<Collect> findBySongListId() {
        return collectMapper.findBySongListId();
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
