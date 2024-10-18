package com.example.controller;

import com.example.common.Result;
import com.example.entity.RankList;
import com.example.entity.Params;
import com.example.service.RankListService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@CrossOrigin
@RestController
@RequestMapping("/rankList")
@Api(tags = "评分管理")
public class RankListController {
    private static final Logger log = LoggerFactory.getLogger(RankListController.class);

    @Resource
    private RankListService rankListService;

    @PostMapping
//    @AutoLog("添加或修改用户")
    public Result save(@RequestBody RankList rankList) {
        if (rankList.getId() == null) {
            rankListService.add(rankList);
        } else {
            rankListService.update(rankList);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<RankList> list = rankListService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
//    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<RankList> info = rankListService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
//    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        rankListService.delete(id);
        return Result.success();
    }

    @GetMapping("/songList")
    public Result findBySongListId() {
        List<RankList> list = rankListService.findBySongListId();
        return Result.success(list);
    }

    @GetMapping("/consumer")
    public Result findByConsumerId() {
        List<RankList> list = rankListService.findByConsumerId();
        return Result.success(list);
    }
    @GetMapping("/avg")
    public Result avgForRankList(Params params) {
//        params.setStatus("0");
        if (params.getStatus()==null||params.getStatus().equals("")){
            params.setStatus("0");
        }
        List<RankList> list = rankListService.avgForRankList(params);
        OptionalDouble averageScore = list.stream()
                .mapToDouble(RankList::getScore)
                .average();
        return Result.success(averageScore);
    }
}
