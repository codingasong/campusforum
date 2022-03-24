package com.dcampusforum.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("goods")
@Data
public class Goods {
   @TableId(value = "goods_id",type = IdType.AUTO)
   private Integer goodsId;
   private String goodsName;
   private String goodsType;
   private String goodsPrice;
   private String goodsDetail;
   private String goodsImg;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   private Date goodsPubdate;
   private String author;

}
