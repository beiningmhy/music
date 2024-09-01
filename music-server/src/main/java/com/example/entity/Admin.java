package com.example.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "music_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "status")
    private String status;
    @Column(name = "login_time")
    private String loginTime;
    @Column(name = "create_time")
    private String createTime;

    @Transient
    private String token;
    @Transient
    private String verCode;

}
