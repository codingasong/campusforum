package com.dcampusforum.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("major")
@Data
public class Major {
    @TableId(value = "major_id", type = IdType.AUTO)
    private int majorId;
    private String majorName;
}
