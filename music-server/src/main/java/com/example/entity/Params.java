package com.example.entity;

import lombok.Data;

@Data
public class Params {
    private String name;
    private String role;
    private String status;
    private String sellStatus;
    private Integer pageNum;
    private Integer pageSize;
    private String username;
    private String singerType;
    private String other;
    private String search;
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

//    商品的ID
    private Integer productId;
//    订单ID
    private Integer orderId;
//    反馈的类型
    private String type;

//    UID
    private String uid;

//    修改密码
    private String oldPw;
    private String newPw;
    private String confirmPw;
}
