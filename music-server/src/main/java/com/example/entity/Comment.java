package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "song_id")
    private String songId;
    @Column(name = "song_list_id")
    private String songListId;
    @Column(name = "content")
    private String content;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "up")
    private String up;
    @Column(name = "down")
    private String down;
    @Column(name = "status")
    private String status;
    @Transient
    private String username;
    @Transient
    private String songName;
    @Transient
    private String songListTitle;
    @Transient
    private String singerName;


}
