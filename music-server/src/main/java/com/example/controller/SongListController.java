package com.example.controller;

import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.SongList;
import com.example.service.SongListService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/songList")
public class SongListController {
    private static final Logger log = LoggerFactory.getLogger(SongListController.class);

    @Resource
    private SongListService songListService;



    @PostMapping
//    @AutoLog("添加或修改用户")
    public Result save(@RequestBody SongList songList) {
        if (songList.getId() == null) {
            songListService.add(songList);
        } else {
            songListService.update(songList);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<SongList> list = songListService.findAll();
        return Result.success(list);
    }
    @GetMapping("/style")
    public Result findAllStyle() {
        List<String> list = songListService.findAllStyle();
        return Result.success(list);
    }
    @PostMapping("/songList/{id}")
    public Result findById(@PathVariable Integer id) {
        List<SongList> songLists = songListService.findBySongId(id);
        return Result.success(songLists);
    }

    @GetMapping("/search")
//    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<SongList> info = songListService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
//    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        songListService.delete(id);
        return Result.success();
    }
}
