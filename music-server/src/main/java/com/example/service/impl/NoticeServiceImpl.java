package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Notice;
import com.example.entity.Params;
import com.example.mapper.NoticeMapper;
import com.example.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;


    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.insertSelective(notice);
    }

    public PageInfo<Notice> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Notice> list = noticeMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.updateByPrimaryKeySelective(notice);
    }

    public void delete(Integer id) {
        noticeMapper.deleteByPrimaryKey(id);
    }

    public List<Notice> findTop() {
        return noticeMapper.findTop3();
    }

    @Override
    public List<Notice> findTopById(Integer id) {
        return noticeMapper.findTopById(id);
    }
}