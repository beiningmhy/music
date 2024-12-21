package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.example.common.JwtTokenUtils;
import com.example.entity.Friend;
import com.example.entity.Imsingle;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.ConsumerMapper;
import com.example.mapper.ImsingleMapper;
import com.example.service.ImsingleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImsingleServiceImpl implements ImsingleService {
    @Resource
    private ImsingleMapper imsingleMapper;
    @Resource
    private ConsumerMapper consumerMapper;

    @Override
    public List<Imsingle> findByUsername(String fromUser, String toUser) {
        List<Imsingle> list = imsingleMapper.findByUsername(toUser,fromUser );
        for(Imsingle imsingle:list){
            imsingle.setReaded("0");
            imsingleMapper.updateByPrimaryKeySelective(imsingle);
        }
        List<Imsingle> chatList = imsingleMapper.findByFromuserAndTouser(fromUser, toUser);
        List<Imsingle> imsingleList = new ArrayList<>();
        for(Imsingle imsingle:chatList){
            imsingle.setFromusername(consumerMapper.selectByPrimaryKey(imsingle.getFromuser()).getUsername());
            imsingle.setFromavatar(consumerMapper.selectByPrimaryKey(imsingle.getFromuser()).getAvatar());
            imsingle.setTousername(consumerMapper.selectByPrimaryKey(imsingle.getTouser()).getUsername());
            imsingle.setToavatar(consumerMapper.selectByPrimaryKey(imsingle.getTouser()).getAvatar());
            imsingleList.add(imsingle);
        }

        return imsingleList;
    }

    @Override
    public Integer findUnReadNums(String fromuser, String touser) {
        return imsingleMapper.findUnReadNums(fromuser, touser);
    }


    @Override
    public List<Imsingle> findAll() {
        return imsingleMapper.selectAll();
    }

    @Override
    public PageInfo<Imsingle> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Imsingle> list = imsingleMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Imsingle imsingle) {


        imsingleMapper.insertSelective(imsingle);
    }

    @Override
    public void update(Imsingle imsingle) {

        imsingleMapper.updateByPrimaryKeySelective(imsingle);
    }

    @Override
    public void delete(Integer id) {
        imsingleMapper.deleteByPrimaryKey(id);
    }




    @Override
    public Imsingle findByById(Integer id) {
        return imsingleMapper.selectByPrimaryKey(id);
    }


}
