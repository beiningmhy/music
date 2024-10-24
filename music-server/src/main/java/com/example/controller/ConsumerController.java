package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Consumer;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.ConsumerService;
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
@RequestMapping("/consumer")
@Api(tags = "用户管理")
public class ConsumerController {
    private static final Logger log = LoggerFactory.getLogger(ConsumerController.class);

    @Resource
    private ConsumerService consumerService;

    @PostMapping("/login")
    @AutoLog("用户登录系统")
    public Result login(@RequestBody Consumer consumer, @RequestParam String key, HttpServletRequest request) {
        if(consumer.getVerCode()==null||consumer.getVerCode().equals("")){
            throw new CustomException("验证码不能为空");
        }

        if(!consumer.getVerCode().toLowerCase().equals(CaptureConfig.CAPTURE_MAP.get(key))){
            // 如果不相等，说明验证不通过
            CaptchaUtil.clear(request);
            CaptureConfig.CAPTURE_MAP.remove(key);
            return Result.error("验证码不正确");
        }
        Consumer loginUser = consumerService.login(consumer);
        CaptureConfig.CAPTURE_MAP.remove(key);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    @AutoLog("用户注册系统")
    public Result register(@RequestBody Consumer consumer) {
        consumerService.add(consumer);
        return Result.success();
    }
    @PostMapping
    @AutoLog("添加或修改用户")
    public Result save(@RequestBody Consumer consumer) {
        if (consumer.getId() == null) {
            consumerService.add(consumer);
        } else {
            consumerService.update(consumer);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Consumer> list = consumerService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Consumer> info = consumerService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        consumerService.delete(id);
        return Result.success();
    }
}
