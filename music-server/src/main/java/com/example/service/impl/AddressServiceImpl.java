package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Address;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.AddressMapper;
import com.example.mapper.AddressMapper;
import com.example.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    @Override
    public List<Address> findAll() {
        return addressMapper.selectAll();
    }

    @Override
    public PageInfo<Address> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        if (params.getParentId() == null || params.getParentId() == 0) {
            params.setParentId(0);
        }
        List<Address> list = null;
        if (params.getStatus().equals("0")) {
            list = addressMapper.findById(params);
        } else if (params.getStatus().equals("1")) {
            list = addressMapper.findByParentId(params);
        }

        return PageInfo.of(list);
    }

    @Override
    public void add(Address address) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (address.getName() == null || "".equals(address.getName())) {
            throw new CustomException("地区名不能为空");
        }

        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了

        Address user = addressMapper.findByName(address.getName());
        if (user != null) {
            // 说明已经有了，这里我们就要提示前台不允许新增了
            throw new CustomException("该地区已存在，请勿重复添加");
        }


        addressMapper.insertSelective(address);
    }

    @Override
    public void update(Address address) {
        addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public void delete(Integer id) {
        addressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Address findById(Integer id) {
        return addressMapper.selectByPrimaryKey(id);
    }


}
