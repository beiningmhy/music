package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "music_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private String time;
    @Column(name = "username")
    private String username;
    @Column(name = "ip")
    private String ip;
    @Column(name = "explorer")
    private String explorer;
    @Column(name = "os")
    private String os;
    @Column(name = "osName")
    private String osName;
    @Column(name = "language")
    private String language;
    @Column(name = "country")
    private String country;
    @Column(name = "hostName")
    private String hostName;
    @Column(name = "hostAddress")
    private String hostAddress;
    @Column(name = "error")
    private String error;
}