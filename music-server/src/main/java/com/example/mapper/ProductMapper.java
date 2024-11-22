package com.example.mapper;

import com.example.entity.Product;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ProductMapper extends Mapper<Product> {
    List<Product> findBySearch(@Param("params") Params params);

//    @Select("select * from music_product where name =#{product.name} and id!=#{product.id} limit 1")
//    @ResultMap("productRM")
    Product findByName(@Param("product") Product product);


}
