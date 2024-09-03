package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_song")
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "singer_id")
    private Integer singerId;
    @Column(name = "name")
    private String name;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "pic")
    private String pic;
    @Column(name = "lyric")
    private String lyric;
    @Column(name = "url")
    private String url;
    @Transient
    private String singerName;




}
