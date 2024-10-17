package com.example.entity;

import lombok.Data;

@Data
public class Params {
    private String name;
    private String role;
    private String status;
    private Integer pageNum;
    private Integer pageSize;
    private String username;
    private String singerType;
    private String other;
    private String singerId;
    private Integer parentId;
    private String title;
    private String style;
    private Integer songListId;
    private Integer songId;
    private String content;
    private Integer userId;
    private Integer consumerId;
//  前端单选框选择的内容
    private String radio;
//    前端单选框选择的id
    private String radioId;
}
