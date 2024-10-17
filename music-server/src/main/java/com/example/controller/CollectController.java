package com.example.controller;

import com.example.common.Result;
import com.example.entity.Collect;
import com.example.entity.Params;
import com.example.service.CollectService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/collect")
@Api(tags = "收藏管理")
public class CollectController {
    private static final Logger log = LoggerFactory.getLogger(CollectController.class);

    @Resource
    private CollectService collectService;

    @PostMapping
//    @AutoLog("添加或修改用户")
    public Result save(@RequestBody Collect collect) {
        if (collect.getId() == null) {
            collectService.add(collect);
        } else {
            collectService.update(collect);
        }
        return Result.success();
    }


    @GetMapping
    public Result findAll() {
        List<Collect> list = collectService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
//    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Collect> info = collectService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
//    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        collectService.delete(id);
        return Result.success();
    }
    @GetMapping("/song")
    public Result findBySongId() {
        List<Collect> list = collectService.findBySongId();
        return Result.success(list);
    }
    @GetMapping("/songList")
    public Result findBySongListId() {
        List<Collect> list = collectService.findBySongListId();
        return Result.success(list);
    }
    @GetMapping("/consumer")
    public Result findByConsumerId() {
        List<Collect> list = collectService.findByConsumerId();
        return Result.success(list);
    }

}
