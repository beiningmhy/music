package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Singer;
import com.example.entity.Params;
import com.example.service.SingerService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/singer")
public class SingerController {
    private static final Logger log = LoggerFactory.getLogger(SingerController.class);

    @Resource
    private SingerService singerService;



    @PostMapping
    @AutoLog("添加或修改歌手")
    public Result save(@RequestBody Singer singer) {
        if (singer.getId() == null) {
            singerService.add(singer);
        } else {
            singerService.update(singer);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Singer> list = singerService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索歌手")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Singer> info = singerService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除歌手")
    public Result delete(@PathVariable Integer id) {
        singerService.delete(id);
        return Result.success();
    }
}
