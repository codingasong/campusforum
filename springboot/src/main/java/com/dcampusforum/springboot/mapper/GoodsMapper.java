package com.dcampusforum.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.entity.Goods;
import com.dcampusforum.springboot.entity.User;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper extends BaseMapper<Goods> {
    Page<Goods> findPage(Page<Object> page, @Param("userName") String search);
}
