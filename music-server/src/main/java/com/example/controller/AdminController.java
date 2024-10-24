package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;


import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员管理")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    @AutoLog("管理员登录系统")
    public Result login(@RequestBody Admin admin, @RequestParam String key, HttpServletRequest request) {
        if(admin.getVerCode()==null||admin.getVerCode().equals("")){
            throw new CustomException("验证码不能为空");
        }

        if(!admin.getVerCode().toLowerCase().equals(CaptureConfig.CAPTURE_MAP.get(key))){
            // 如果不相等，说明验证不通过
            CaptchaUtil.clear(request);
            CaptureConfig.CAPTURE_MAP.remove(key);
            return Result.error("验证码不正确");
        }
        Admin loginUser = adminService.login(admin);
        CaptureConfig.CAPTURE_MAP.remove(key);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    @AutoLog("管理员注册系统")
    public Result register(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }
    @PostMapping
    @AutoLog("添加或修改管理员")
    public Result save(@RequestBody Admin admin) {
        if (admin.getId() == null) {
            adminService.add(admin);
        } else {
            adminService.update(admin);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Admin> list = adminService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索管理员")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Admin> info = adminService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除管理员")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return Result.success();
    }
    @GetMapping("/statusCount")
    public Result statusCount() {
        List<Admin> admins=adminService.findAll();
        Map<String, Long> collect = admins.stream()
                .collect(Collectors.groupingBy(
                        admin -> {
                            String sex=admin.getStatus();
                            if ("0".equals(sex)) {
                                return "正常";
                            } else if ("1".equals(sex)) {
                                return "封禁";
                            } else {
                                return "未知";
                            }
                        },
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
}
