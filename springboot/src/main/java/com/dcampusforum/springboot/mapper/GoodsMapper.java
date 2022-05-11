package com.dcampusforum.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.entity.Goods;
import com.dcampusforum.springboot.entity.User;
import com.dcampusforum.springboot.vo.GoodsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GoodsMapper extends BaseMapper<Goods> {
    Page<Goods> findPage(Page<Object> page, @Param("userName") String search);

    @Select("SELECT g.*, u.email  FROM goods g, USER u  WHERE g.author = u.user_name AND g.goods_id= #{goodsId}")
    GoodsVO getGoodsVO(Integer goodsId);
}
