package com.example.controller;


import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Order;
import com.example.service.OrderService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;



@CrossOrigin
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;

    @PostMapping
    @AutoLog("添加或修改订单")
    public Result save(@RequestBody Order order) {
        if (order.getId() == null) {
            orderService.add(order);
        } else {
            orderService.update(order);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Order> list = orderService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索订单")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Order> info = orderService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除订单")
    public Result delete(@PathVariable Integer id) {
        orderService.delete(id);
        return Result.success();
    }
    @GetMapping("/user/{id}")
    public Result findByUserId(@PathVariable Integer id){
        return Result.success(orderService.findByUserId(id));
    }
    @GetMapping("/product/{id}")
    public Result findByProductId(@PathVariable Integer id){
        return Result.success(orderService.findByProductId(id));
    }

    @PostMapping("/status/{id}")
    public Result updateStatus(@PathVariable Integer id){
        Order order=orderService.findByById(id);
        order.setStatus("1");
        orderService.update(order);
        return Result.success();
    }




}
