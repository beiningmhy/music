package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.JwtTokenUtils;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public List<Admin> findAll() {
        return adminMapper.selectAll();
    }

    @Override
    public PageInfo<Admin> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Admin> list = adminMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Admin admin) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("用户名不能为空");
        }
        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        Admin user = adminMapper.findByName(admin.getName());
        if (user != null) {
            // 说明已经有了，这里我们就要提示前台不允许新增了
            throw new CustomException("该用户名已存在，请更换用户名");
        }
        // 初始化一个密码
        if (admin.getPassword() == null || admin.getPassword().equals("")) {
            admin.setPassword("123456");

        }
        //若没有角色，则默认为歌曲管理员
        if (admin.getRole() == null || admin.getRole().equals("")) {
            admin.setRole("1");
        }
        String time = DateUtil.now();

        admin.setCreateTime(time);
        admin.setStatus("0");
        adminMapper.insertSelective(admin);
    }

    @Override
    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void delete(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }


    @Override
    public Admin login(Admin admin) {
        // 1. 进行一些非空判断
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("用户名不能为空");
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        // 2. 从数据库里面根据这个用户名和密码去查询对应的管理员信息，
        Admin user = adminMapper.findByNameAndPassword(admin.getName(), admin.getPassword());
        if (user == null) {
            // 如果查出来没有，那说明输入的用户名或者密码有误，提示用户，不允许登录
            throw new CustomException("用户名或密码输入错误");
        }
        if(user.getStatus().equals("1")){
            throw new CustomException("该用户已被禁用");
        }
        user.setLoginTime(DateUtil.now());
        adminMapper.updateByPrimaryKeySelective(user);
        // 如果查出来了有，那说明确实有这个管理员，而且输入的用户名和密码都对；
        // 生成jwt token给前端
        String token = JwtTokenUtils.genToken(user.getId().toString(), user.getPassword());
        user.setToken(token);
        return user;
    }

    @Override
    public Admin findByById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
