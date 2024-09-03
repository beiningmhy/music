package com.example.controller;

import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Song;
import com.example.service.SongService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/song")
public class SongController {
    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    @Resource
    private SongService songService;



    @PostMapping
//    @AutoLog("添加或修改用户")
    public Result save(@RequestBody Song song) {
        if (song.getId() == null) {
            songService.add(song);
        } else {
            songService.update(song);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Song> list = songService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
//    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Song> info = songService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
//    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        songService.delete(id);
        return Result.success();
    }
}
