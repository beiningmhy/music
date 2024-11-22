package com.example.service;

import com.example.entity.Params;
import com.example.entity.Order;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    PageInfo<Order> findBySearch(Params params);

    void add(Order order);

    void update(Order order);

    void delete(Integer id);

    Order findByById(Integer id);
    Order findByUserIdAndProductId(Integer userId,Integer productId);
    List<Order> findByUserId(Integer id);
    List<Order> findByProductId(Integer id);

}
