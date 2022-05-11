package com.dcampusforum.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author shd
 * @since 2022-05-07
 */
@TableName("discuss")
@Data
public class Discuss {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String content;

    private String userName;

    private String belong;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pubdate;
}
