package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.JwtTokenUtils;
import com.example.entity.Feedback;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.FeedbackMapper;
import com.example.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public List<Feedback> findAll() {
        return feedbackMapper.selectAll();
    }

    @Override
    public PageInfo<Feedback> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Feedback> list = feedbackMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Feedback feedback) {
        String time = DateUtil.now();
        feedback.setCreateTime(time);
        feedback.setStatus("0");

        feedbackMapper.insertSelective(feedback);
    }

    @Override
    public void update(Feedback feedback) {

        feedbackMapper.updateByPrimaryKeySelective(feedback);
    }

    @Override
    public void delete(Integer id) {
        feedbackMapper.deleteByPrimaryKey(id);
    }



    @Override
    public Feedback findByById(Integer id) {
        return feedbackMapper.selectByPrimaryKey(id);
    }


}
