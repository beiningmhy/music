package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Table(name = "music_consumer")
@AllArgsConstructor
@NoArgsConstructor
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="uid")
    private String uid;

    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="sex")
    private String sex;
    @Column(name="phone")
    private String phone;
    @Column(name="email")
    private String email;
    @Column(name="birth")
    private String birth;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "address")
    private String address;
    @Column(name = "avatar")
    private String avatar;
    @Column(name="point")
    private Integer point;
    @Column(name ="singer_type")
    private String singerType;
    @Column(name ="create_time")
    private String createTime;
    @Column(name ="update_time")
    private String updateTime;
    @Column(name ="login_time")
    private String loginTime;
    @Column(name ="status")
    private String status;

    @Transient
    private String token;
    @Transient
    private String verCode;




}
