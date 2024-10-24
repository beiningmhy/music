package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Address;
import com.example.exception.CustomException;
import com.example.service.SingerService;
import com.example.service.AddressService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/address")
@Api(tags = "地址管理")
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Resource
    private AddressService addressService;
    @Resource
    private SingerService singerService;


    @PostMapping
    @AutoLog("添加或修改地址")
    public Result save(@RequestBody Address address) {
        if (address.getId() == null) {
            addressService.add(address);
        } else {
            addressService.update(address);
        }
        return Result.success();
    }

    @GetMapping("/map")
    @AutoLog("查询所有地址")
    public Result findAll() {
        List<Address> list = addressService.findAll();
        return Result.success(transformToOptions(list));
    }
    @GetMapping("/{id}")
    @AutoLog("根据ID查找地址")
    public Result findById(@PathVariable Integer id) {
        return Result.success(addressService.findById(id).getName());
    }

    @GetMapping("/search")
    @AutoLog("搜索地址")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Address> info = addressService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除地址")
    public Result delete(@PathVariable Integer id) {
        addressService.delete(id);
        return Result.success();
    }

    public static List<Option> transformToOptions(List<Address> addresses) {
        Map<String, Option> optionMap = new HashMap<>();
        List<Option> rootOptions = new ArrayList<>();

        // 创建Option对象并存入Map
        for (Address address : addresses) {
            Option option = new Option(address.getId().toString(), address.getName());
            optionMap.put(address.getId().toString(), option);

            // 如果该地址的parentId为空或不存在于optionMap中，则认为它是根节点
            if (address.getParentId() == null || !optionMap.containsKey(address.getParentId())) {
                rootOptions.add(option);
            } else {
                // 添加到其父节点的children列表中
                Option parent = optionMap.get(address.getParentId());
                if (parent != null) {
                    parent.addChild(option);
                }
            }
        }

        // 移除没有子节点的children
        pruneEmptyChildren(rootOptions);

        return rootOptions;
    }

    private static void pruneEmptyChildren(List<Option> options) {
        for (Option option : options) {
            if (option.getChildren().isEmpty()) {
                option.setChildren(null); // 直接删除children列表
            } else {
                // 递归处理子节点
                pruneEmptyChildren(option.getChildren());
            }
        }
    }

    public static class Option {
        private String value;
        private String label;
        private List<Option> children;

        public Option(String value, String label) {
            this.value = value;
            this.label = label;
            this.children = new ArrayList<>();
        }

        public void addChild(Option child) {
            this.children.add(child);
        }

        // getters 和 setters
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<Option> getChildren() {
            return children;
        }

        public void setChildren(List<Option> children) {
            this.children = children;
        }
    }

}
