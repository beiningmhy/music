package com.example.controller;

import com.example.service.SimpleOrderManager;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/email")
@Api(tags = "邮箱管理")

public class EmailController {

    @Resource
    private SimpleOrderManager emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestParam("to") String to,
                            @RequestParam("subject") String subject,
                            @RequestParam("body") String body) {
        emailService.sendSimpleEmail(to, subject, body);
        return "Email sent successfully";
    }
}