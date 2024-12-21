package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "music_imsingle")
@AllArgsConstructor
@NoArgsConstructor
public class Imsingle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;
    @Column(name = "fromuser")
    private String fromuser;
    @Column(name = "fromavatar")
    private String fromavatar;
    @Column(name = "time")
    private String time;
    @Column(name="type")
    private String type;
    @Column(name = "touser")
    private String touser;
    @Column(name = "toavatar")
    private String toavatar;
    @Column(name="readed")
    private String readed;

    @Transient
    private String fromusername;
    @Transient
    private String tousername;

}
