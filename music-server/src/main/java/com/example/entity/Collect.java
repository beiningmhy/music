package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_collect")
@AllArgsConstructor
@NoArgsConstructor
public class Collect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "song_id")
    private Integer songId;
    @Column(name = "song_list_id")
    private Integer songListId;
    @Column(name = "create_time")
    private String createTime;

    @Column(name = "status")
    private String status;
    @Transient
    private String username;
    @Transient
    private String songName;
    @Transient
    private String songListTitle;


}
