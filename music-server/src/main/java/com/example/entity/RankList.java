package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_rank_list")
@AllArgsConstructor
@NoArgsConstructor
public class RankList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "song_list_id")
    private Integer songListId;
    @Column(name="consumer_id")
    private Integer consumerId;
    @Column(name = "score")
    private Integer score;
    @Column(name = "status")
    private String status;

    @Transient
    private String username;
    @Transient
    private String songListTitle;



}
