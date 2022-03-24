package com.dcampusforum.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("job")
@Data
public class Job {
    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;
    private String jobName;
    private String jobDetail;
    private String jobType;
    private String jobPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date jobPubdate;
    private String author;

}
