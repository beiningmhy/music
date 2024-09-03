package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.JwtTokenUtils;
import com.example.entity.Consumer;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.ConsumerMapper;
import com.example.service.ConsumerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Resource
    private ConsumerMapper consumerMapper;

    @Override
    public List<Consumer> findAll() {
        return consumerMapper.selectAll();
    }

    @Override
    public PageInfo<Consumer> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Consumer> list = consumerMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Consumer consumer) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (consumer.getUsername() == null || "".equals(consumer.getUsername())) {
            throw new CustomException("用户名不能为空");
        }
        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        Consumer user = consumerMapper.findByUsername(consumer.getUsername());
        if (user != null) {
            // 说明已经有了，这里我们就要提示前台不允许新增了
            throw new CustomException("该用户名已存在，请更换用户名");
        }
        // 初始化一个密码
        if (consumer.getPassword() == null || consumer.getPassword().equals("")) {
            consumer.setPassword("123456");

        }
        //若没有角色，则默认为普通用户
        if (consumer.getSingerType() == null || consumer.getSingerType().equals("")) {
            consumer.setSingerType("0");
        }
        String time = DateUtil.now();

        consumer.setCreateTime(time);
        consumer.setUpdateTime(time);
        consumer.setStatus("1");//注册先置为禁用，登录后才为启用
        consumerMapper.insertSelective(consumer);
    }

    @Override
    public void update(Consumer consumer) {
        consumerMapper.updateByPrimaryKeySelective(consumer);
    }

    @Override
    public void delete(Integer id) {
        consumerMapper.deleteByPrimaryKey(id);
    }


    @Override
    public Consumer login(Consumer consumer) {
        // 1. 进行一些非空判断
        if (consumer.getUsername() == null || "".equals(consumer.getUsername())) {
            throw new CustomException("用户名不能为空");
        }
        if (consumer.getPassword() == null || "".equals(consumer.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        // 2. 从数据库里面根据这个用户名和密码去查询对应的管理员信息，
        Consumer user = consumerMapper.findByUsernameAndPassword(consumer.getUsername(), consumer.getPassword());
        if (user == null) {
            // 如果查出来没有，那说明输入的用户名或者密码有误，提示用户，不允许登录
            throw new CustomException("用户名或密码输入错误");
        }
        //判断是否有登录时间，如果没有登录时间且账号状态为禁用表示从未登录那就将状态改为可用，否则提示账号被禁用

        if(user.getStatus().equals("1")&&user.getLoginTime()==null){
            user.setStatus("0");
        }else if(user.getStatus().equals("1")){
            throw new CustomException("该用户已被禁用");
        }
        user.setLoginTime(DateUtil.now());
        consumerMapper.updateByPrimaryKeySelective(user);
        // 如果查出来了有，那说明确实有这个管理员，而且输入的用户名和密码都对；
        // 生成jwt token给前端
        String token = JwtTokenUtils.genToken(user.getId().toString(), user.getPassword());
        user.setToken(token);
        return user;
    }

    @Override
    public Consumer findByById(Integer id) {
        return consumerMapper.selectByPrimaryKey(id);
    }
}
