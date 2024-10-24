package com.example.service.impl;

import com.example.entity.Log;
import com.example.entity.Params;
import com.example.mapper.LogMapper;
import com.example.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;


    public void add(Log log) {
        logMapper.insertSelective(log);
    }


    public PageInfo<Log> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Log> list = logMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public List<Log> findByUserId() {
        return logMapper.findByUserId() ;
    }

    public void delete(Integer id) {
        logMapper.deleteByPrimaryKey(id);
    }

}
