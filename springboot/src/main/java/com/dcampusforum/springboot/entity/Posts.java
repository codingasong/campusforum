package com.dcampusforum.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("posts")
@Data
public class Posts {
    @TableId(value = "posts_id",type = IdType.AUTO)
    private Integer postsId;
    private String postsTitle;
    private String postsDetail;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date postsPubdate;
    private String author;

}
