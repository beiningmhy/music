package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.ListSong;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.ListSongService;
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
@RequestMapping("/listSong")
@Api(tags = "歌单歌曲管理")
public class ListSongController {
    private static final Logger log = LoggerFactory.getLogger(ListSongController.class);

    @Resource
    private ListSongService listSongService;


    @PostMapping
    @AutoLog("将歌曲添加或修改到歌单中")
    public Result save(@RequestBody ListSong listSong) {
        if (listSong.getId() == null) {
            listSongService.add(listSong);
        } else {
            listSongService.update(listSong);
        }
        return Result.success();
    }





    @DeleteMapping("/{songId}/{songListId}")
    @AutoLog("将歌曲从歌单中移除")
    public Result delete(@PathVariable Integer songId,@PathVariable Integer songListId) {
        listSongService.delete(songId,songListId);
        return Result.success();
    }
}
