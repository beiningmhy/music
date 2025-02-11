package com.example.controller;

import com.example.common.AutoLog;
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

    @PostMapping("/save")
    @AutoLog("添加或修改评论")
    public Result save(@RequestBody Comment comment) {
        if (comment.getId() == null) {
            commentService.add(comment);
        } else {
            commentService.update(comment);
        }
        return Result.success();
    }
    @PostMapping("/up")
    @AutoLog("点赞评论")
    public Result up(@RequestBody Comment comment) {
        Comment comment1 = commentService.findById(comment.getId());
        comment1.setUp(String.valueOf(Integer.parseInt(comment1.getUp())+1));
        commentService.update(comment1);
        return Result.success();
    }
    @PostMapping("/down")
    @AutoLog("反对评论")
    public Result down(@RequestBody Comment comment) {
        Comment comment1 = commentService.findById(comment.getId());
        comment1.setDown(String.valueOf(Integer.parseInt(comment1.getDown())+1));
        commentService.update(comment1);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Comment> list = commentService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索评论")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Comment> info = commentService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除评论")
    public Result delete(@PathVariable Integer id) {
        commentService.delete(id);
        return Result.success();
    }
    @PostMapping("/status/{id}")
    public Result UPStatus(@PathVariable Integer id) {
        Comment comment = commentService.findById(id);
        comment.setStatus("1");
        commentService.update(comment);
        return Result.success();
    }
//    @GetMapping("/song")
//    public Result findBySongId() {
//        List<Comment> list = commentService.findBySongId();
//        return Result.success(list);
//    }
//    @GetMapping("/songList")
//    public Result findBySongListId() {
//
//        List<Comment> list = commentService.findBySongListId();
//        return Result.success(list);
//    }
//    @GetMapping("/singer")
//    public Result findBySingerId() {
//        List<Comment> list = commentService.findBySingerId();
//        return Result.success(list);
//    }
    @GetMapping("/song/{id}")
    public Result findBySongId2(@PathVariable Integer id) {
//        System.out.println("id:"+id);
        List<Comment> list = commentService.findBySongId2(id);
        return Result.success(list);
    }
    @GetMapping("/songList/{id}")
    public Result findBySongListId2(@PathVariable Integer id) {

        List<Comment> list = commentService.findBySongListId2(id);
        return Result.success(list);
    }
    @GetMapping("/singer/{id}")
    public Result findBySingerId2(@PathVariable Integer id) {
        List<Comment> list = commentService.findBySingerId2(id);
        return Result.success(list);
    }
    @GetMapping("/consumer")
    public Result findByConsumerId() {
        List<Comment> list = commentService.findByConsumerId();
        return Result.success(list);
    }
}
