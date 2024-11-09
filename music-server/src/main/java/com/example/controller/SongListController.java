package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Singer;
import com.example.entity.SongList;
import com.example.service.SongListService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/songList")
@Api(tags = "歌单管理")
public class SongListController {
    private static final Logger log = LoggerFactory.getLogger(SongListController.class);

    @Resource
    private SongListService songListService;



    @PostMapping
    @AutoLog("添加或修改歌单")
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
    public Result findBySongId(@PathVariable Integer id) {
        List<SongList> songLists = songListService.findBySongId(id);
        return Result.success(songLists);
    }
    @PostMapping("/{id}")
    public Result findByById(@PathVariable Integer id) {
        SongList songList = songListService.findByById(id);
        return Result.success(songList);
    }


    @GetMapping("/search")
    @AutoLog("搜索歌单")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<SongList> info = songListService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除歌单")
    public Result delete(@PathVariable Integer id) {
        songListService.delete(id);
        return Result.success();
    }
    @GetMapping("/top/{num}")
    public Result top(@PathVariable String num) {
        Params params = new Params();
        params.setPageNum(1);
        params.setPageSize(1);
        PageInfo<SongList> tmp = songListService.findBySearch(params);
        params.setPageSize((int) tmp.getTotal());
        PageInfo<SongList> search = songListService.findBySearch(params);
        List<SongList> list = search.getList();
        // 使用stream流获取点击量最高的10个Singer
        List<SongList> topSongLists = list.stream()
                .sorted(Comparator.comparingInt(SongList::getClicks).reversed()) // 根据点击量降序排序
                .limit(Long.parseLong(num)) // 限制结果为10个
                .collect(Collectors.toList()); // 收集结果到List
        return Result.success(topSongLists);
    }
    @PostMapping("/clicks")
    public Result updateClicks(@RequestBody SongList songList) {
        SongList byId = songListService.findByById(songList.getId());
        byId.setClicks(byId.getClicks()+1);
        songListService.update(byId);
        return Result.success();

    }
}
