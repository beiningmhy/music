package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_list_song")
@AllArgsConstructor
@NoArgsConstructor
public class ListSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "song_id")
    private Integer songId;

    @Column(name = "song_list_id")
    private Integer songListId;

}
