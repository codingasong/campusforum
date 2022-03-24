package com.dcampusforum.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("user")
@Data
public class User {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;
    private String userName;
    private String password;
    private String gender;
    private String email;
    private String major;
    private Integer age;
    private Integer role;
    private String avatar;

    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private List<Goods> goodsList;

}
