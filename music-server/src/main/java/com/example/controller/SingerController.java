package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Singer;
import com.example.entity.Params;
import com.example.service.SingerService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/singer")
@Api(tags = "歌手管理")
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

    @GetMapping("/sexCount")
    public Result sexCount() {
        List<Singer> singers = singerService.findAll();
        Map<String, Long> collect = singers.stream()
                .collect(Collectors.groupingBy(
                        // 定义一个函数来处理sex属性
                        singer -> {
                            String sex = singer.getSex();
                            if ("1".equals(sex)) {
                                return "男";
                            } else if ("0".equals(sex)) {
                                return "女";
                            } else {
                                return "未知"; // 如果sex为空或者不是"0"或"1"
                            }
                        },
                        // 使用counting()收集器来计算每个分组的数量
                        Collectors.counting()
                ));
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", collect.get(key));
            mapList.add(map);
        }
        return Result.success(mapList);
    }

    @GetMapping("/top/{num}")
    public Result top(@PathVariable String num) {
        Params params = new Params();
        params.setPageNum(1);
        params.setPageSize(1);
        PageInfo<Singer> tmp = singerService.findBySearch(params);
        params.setPageSize((int) tmp.getTotal());
        PageInfo<Singer> search = singerService.findBySearch(params);
        List<Singer> list = search.getList();
        // 使用stream流获取点击量最高的10个Singer
        List<Singer> topSingers = list.stream()
                .sorted(Comparator.comparingInt(Singer::getClicks).reversed()) // 根据点击量降序排序
                .limit(Long.parseLong(num)) // 限制结果为10个
                .collect(Collectors.toList()); // 收集结果到List
        return Result.success(topSingers);
    }
    @PostMapping("/clicks")
    public Result updateClicks(@RequestBody Singer singer) {
        Singer byId = singerService.findByById(singer.getId());
        byId.setClicks(byId.getClicks()+1);
        singerService.update(byId);
        return Result.success();

    }
}
