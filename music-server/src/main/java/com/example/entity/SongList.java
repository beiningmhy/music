package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_song_list")
@AllArgsConstructor
@NoArgsConstructor
public class SongList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;
    @Column(name="pic")
    private String pic;
    @Column(name="introduction")
    private String introduction;
    @Column(name="style")
    private String style;
    @Column(name="clicks")
    private Integer clicks;




}
