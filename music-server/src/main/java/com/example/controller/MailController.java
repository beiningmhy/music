package com.example.controller;

import com.example.common.MailConfig;
import com.example.common.Result;
import com.example.service.MailService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping
@Api(tags = "邮件验证码")
public class MailController {
    @Resource
    private MailService mailService;

    @RequestMapping("/MailCode")
    public Result sendMailCode(@RequestParam String toEmail, @RequestParam String key) {
        //生成6为随机数
        int min = 100000; // 6位数最小值
        int max = 999999; // 6位数最大值
        int randomNumber = (int) (Math.random() * (max - min + 1)) + min;
        String code = String.valueOf(randomNumber);
        MailConfig.MAIL_MAP.put(key, code);
        String subject = "MUSIC验证码";
        String content = "您的验证码是：" + code;
        mailService.sendTextMailMessage(toEmail, subject, content);
        return Result.success();

    }
}
