package com.example.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "music_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;
    @Column(name = "time")
    private String time;
    @Column(name="status")
    private String status;
}