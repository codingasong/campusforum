package com.dcampusforum.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcampusforum.springboot.entity.Goods;
import com.dcampusforum.springboot.entity.User;
import com.dcampusforum.springboot.vo.GoodsVO;

public interface GoodsService extends IService<Goods> {
    Page<Goods> findPage(Page<Object> objectPage, String search);
    GoodsVO getGoodsVO(Integer goodsId);
}
