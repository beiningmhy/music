package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.JwtTokenUtils;
import com.example.entity.Banner;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.BannerMapper;
import com.example.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Resource
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> findAll() {
        return bannerMapper.findAll();
    }

    @Override
    public PageInfo<Banner> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Banner> list = bannerMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Banner banner) {


        bannerMapper.insertSelective(banner);
    }

    @Override
    public void update(Banner banner) {

        bannerMapper.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void delete(Integer id) {
        bannerMapper.deleteByPrimaryKey(id);
    }




    @Override
    public Banner findByById(Integer id) {
        return bannerMapper.selectByPrimaryKey(id);
    }



}
