package com.example.mapper;

import com.example.entity.Params;
import com.example.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderMapper extends Mapper<Order> {
    List<Order> findBySearch(@Param("params") Params params);

//    @Select("select * from music_order where name =#{order.name} and id!=#{order.id} limit 1")
//    @ResultMap("orderRM")
    Order findByName(@Param("order") Order order);

    @Select("select * from music_order where user_id=#{userId} and product_id=#{productId} limit 1")
    Order findByUserIdAndProductId(@Param("userId") Integer userId,@Param("productId") Integer productId);

    @Select("select * from music_order where user_id=#{userId} and product_id=#{productId} and status='0' ")
    List<Order> findByUserIdAndProductIdList(@Param("userId") Integer userId,@Param("productId") Integer productId);

    List<Order> findByUserId(@Param("id") Integer id);
    List<Order> findByProductId(@Param("id") Integer id);

}
