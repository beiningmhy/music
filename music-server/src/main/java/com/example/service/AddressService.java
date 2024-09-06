package com.example.service;

import com.example.entity.Address;
import com.example.entity.Params;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AddressService {
    List<Address> findAll();

    PageInfo<Address> findBySearch(Params params);

    void add(Address address);

    void update(Address address);

    void delete(Integer id);
    Address findById(Integer id);


}
