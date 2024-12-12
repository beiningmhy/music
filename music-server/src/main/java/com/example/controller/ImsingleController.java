package com.example.controller;

import com.example.common.Result;
import com.example.service.ImsingleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/imsingle")
public class ImsingleController {
    @Resource
    private ImsingleService imsingleService;

    /**
     * 查询所有消息
     */
    @GetMapping
    public Result findByFromUsername(@RequestParam String fromUser, @RequestParam String toUser) {
        return Result.success(imsingleService.findByUsername(fromUser, toUser));

    }
    /**
     * 查询未读消息数量
     */
    @GetMapping("/unReadNums")
    public Result findUnReadNums(@RequestParam String toUsername) {
        return Result.success(imsingleService.findUnReadNums(toUsername));
    }
}
