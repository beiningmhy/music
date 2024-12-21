package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_friend")
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "friend1")
    private String friend1;
    @Column(name = "friend1avatar")
    private String friend1avatar;
    @Column(name = "friend2")
    private String friend2;
    @Column(name = "friend2avatar")
    private String friend2avatar;
    @Transient
    private Integer num;

}
