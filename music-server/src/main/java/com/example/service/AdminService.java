package com.example.service;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    List<Admin> findAll();

    PageInfo<Admin> findBySearch(Params params);

    void add(Admin admin);

    void update(Admin admin);

    void delete(Integer id);

    Admin login(Admin admin);

    Admin findByById(Integer id);
}
