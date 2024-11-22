package com.example.service;

import com.example.entity.Product;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    PageInfo<Product> findBySearch(Params params);

    void add(Product product);

    void update(Product product);

    void delete(Integer id);



    Product findByById(Integer id);

}
