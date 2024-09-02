package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Table(name = "music_admin")
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "login_time")
    private String loginTime;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "status")
    private String status;
    @Transient
    private String token;
    @Transient
    private String verCode;
    @Transient
    private boolean sts;

}
