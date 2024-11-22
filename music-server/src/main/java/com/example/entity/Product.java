package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name="content")
    private String content;
    @Column(name="url")
    private String url;
    @Column(name="price")
    private Double price;
    @Column(name="amount")
    private Integer amount;
    @Column(name="max")
    private Integer max;
    @Column(name="sell_time")
    private String sellTime;
    @Column(name="create_time")
    private String createTime;
    @Column(name="sell_status")
    private String sellStatus;
    @Column(name="status")
    private String status;


}
