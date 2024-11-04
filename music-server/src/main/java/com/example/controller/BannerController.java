package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Banner;
import com.example.entity.Params;
import com.example.service.BannerService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/banner")
@Api(tags = "轮播图管理")
public class BannerController {
    private static final Logger log = LoggerFactory.getLogger(BannerController.class);

    @Resource
    private BannerService bannerService;


    @PostMapping
    @AutoLog("添加或修改轮播图")
    public Result save(@RequestBody Banner banner) {
        if (banner.getId() == null) {
            bannerService.add(banner);
        } else {
            bannerService.update(banner);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Banner> list = bannerService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索轮播图")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Banner> info = bannerService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除轮播图")
    public Result delete(@PathVariable Integer id) {
        bannerService.delete(id);
        return Result.success();
    }

}
