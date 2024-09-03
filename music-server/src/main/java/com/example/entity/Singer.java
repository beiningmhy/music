package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_singer")
@AllArgsConstructor
@NoArgsConstructor
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name="sex")
    private String sex;
    @Column(name="pic")
    private String pic;
    @Column(name="birth")
    private String birth;
    @Column(name="address")
    private String address;
    @Column(name="introduction")
    private String introduction;




}
