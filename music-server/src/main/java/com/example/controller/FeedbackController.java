package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Feedback;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.FeedbackService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/feedback")
@Api(tags = "反馈管理")
public class FeedbackController {
    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);

    @Resource
    private FeedbackService feedbackService;

    @PostMapping
    @AutoLog("添加或修改反馈")
    public Result save(@RequestBody Feedback feedback) {
        if (feedback.getId() == null) {
            feedbackService.add(feedback);
        } else {
            feedbackService.update(feedback);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Feedback> list = feedbackService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索反馈")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Feedback> info = feedbackService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除反馈")
    public Result delete(@PathVariable Integer id) {
        feedbackService.delete(id);
        return Result.success();
    }
    @GetMapping("/type")
    public Result findByType(){
        List<Feedback> list = feedbackService.findAll();
        List<String> typeList = list.stream().map(Feedback::getType).distinct().collect(Collectors.toList());
        return Result.success(typeList);
    }

}
