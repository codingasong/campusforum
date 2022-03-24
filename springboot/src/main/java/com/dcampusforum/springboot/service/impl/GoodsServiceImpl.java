package com.dcampusforum.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcampusforum.springboot.entity.Goods;
import com.dcampusforum.springboot.entity.User;
import com.dcampusforum.springboot.mapper.GoodsMapper;
import com.dcampusforum.springboot.mapper.UserMapper;
import com.dcampusforum.springboot.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Resource
    GoodsMapper goodsMapper;
    @Override
    public Page<Goods> findPage(Page<Object> objectPage, String search) {
        LambdaQueryWrapper<Goods> wrapper = Wrappers.<Goods>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Goods::getGoodsName, search);
        }
        Page<Goods> goodsPage = goodsMapper.findPage(objectPage, search);
        return goodsPage;
    }
}
