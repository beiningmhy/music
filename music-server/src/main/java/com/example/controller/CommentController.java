package com.example.controller;

import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Comment;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.CommentService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comment")
@Api(tags = "评论管理")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private CommentService commentService;

    @PostMapping
//    @AutoLog("添加或修改用户")
    public Result save(@RequestBody Comment comment) {
        if (comment.getId() == null) {
            commentService.add(comment);
        } else {
            commentService.update(comment);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Comment> list = commentService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
//    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Comment> info = commentService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
//    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        commentService.delete(id);
        return Result.success();
    }
    @GetMapping("/song")
    public Result findBySongId() {
        List<Comment> list = commentService.findBySongId();
        return Result.success(list);
    }
    @GetMapping("/songList")
    public Result findBySongListId(Integer id) {
        List<Comment> list = commentService.findBySongListId();
        return Result.success(list);
    }
    @GetMapping("/singer")
    public Result findBySingerId(Integer id) {
        List<Comment> list = commentService.findBySingerId();
        return Result.success(list);
    }
}
