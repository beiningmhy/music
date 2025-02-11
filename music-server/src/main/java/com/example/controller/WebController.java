package com.example.controller;

import com.example.common.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/web")
@Api(tags = "web管理")
public class WebController {
    @GetMapping
    public Result index() {
        return Result.success();
    }
    @GetMapping("/user")
    public Result user() {
        return Result.success();
    }
}
