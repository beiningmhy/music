package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Address;
import com.example.exception.CustomException;
import com.example.service.SingerService;
import com.example.service.AddressService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/address")
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Resource
    private AddressService addressService;
    @Resource
    private SingerService singerService;



    @PostMapping
//    @AutoLog("添加或修改用户")
    public Result save(@RequestBody Address address) {
        if (address.getId() == null) {
            addressService.add(address);
        } else {
            addressService.update(address);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Address> list = addressService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
//    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Address> info = addressService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
//    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        addressService.delete(id);
        return Result.success();
    }


}
