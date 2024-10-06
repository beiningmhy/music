package com.example.entity;

import cn.hutool.core.annotation.Alias;
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
    @Alias("歌曲ID")
    private Integer id;

    @Column(name = "singer_id")
    private Integer singerId;
    @Column(name = "name")
    @Alias("歌曲名称")
    private String name;
    @Column(name = "introduction")
    @Alias("歌曲介绍")
    private String introduction;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "pic")
    @Alias("歌曲图片")
    private String pic;
    @Column(name = "lyric")
    @Alias("歌曲歌词")
    private String lyric;
    @Column(name = "url")
    @Alias("歌曲地址")
    private String url;
    @Transient
    @Alias("歌手名称")
    private String singerName;
    @Transient
    @Alias("所属歌单名称")
    private String songListTitle;




}
