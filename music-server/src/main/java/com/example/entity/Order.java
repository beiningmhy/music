package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;
    @Column(name="product_id")
    private Integer productId;
    @Column(name="num")
    private Integer num;
    @Column(name="price")
    private Double price;
    @Column(name="create_time")
    private String createTime;
    @Column(name="status")
    private String status;
    @Transient
    private String productName;
    @Transient
    private String username;
    @Transient
    private String url;



}
