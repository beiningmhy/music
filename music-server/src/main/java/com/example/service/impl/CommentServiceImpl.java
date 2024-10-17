package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.JwtTokenUtils;
import com.example.entity.Comment;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.CommentMapper;
import com.example.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findAll() {
        return commentMapper.selectAll();
    }

    @Override
    public PageInfo<Comment> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Comment> list = commentMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Comment comment) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (comment.getContent() == null || "".equals(comment.getContent())) {
            throw new CustomException("评论内容不能为空");
        }

        String time = DateUtil.now();

        comment.setCreateTime(time);
        if (comment.getStatus()==null){
            comment.setStatus("1");//注册先置为禁用，登录后才为启用
        }

        commentMapper.insertSelective(comment);
    }

    @Override
    public void update(Comment comment) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (comment.getContent() == null || "".equals(comment.getContent())) {
            throw new CustomException("评论内容不能为空");
        }

        commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public void delete(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Comment> findBySongId() {
        return commentMapper.findBySongId();
    }

    @Override
    public List<Comment> findBySongListId() {
        return commentMapper.findBySongListId();
    }

    @Override
    public List<Comment> findBySingerId() {
        return commentMapper.findBySingerId();
    }

    @Override
    public Comment findById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Comment> findByConsumerId() {
        return commentMapper.findByConsumerId();
    }


}
