package com.dcampusforum.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.common.Result;
import com.dcampusforum.springboot.entity.Goods;
import com.dcampusforum.springboot.entity.User;
import com.dcampusforum.springboot.service.GoodsService;
import com.dcampusforum.springboot.vo.GoodsVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    GoodsService goodsService;


    @PostMapping
    public Result<?> addGoods(@RequestBody Goods goods) {
        if(goods.getGoodsPubdate() == null) {
            goods.setGoodsPubdate(new Date());//设置时间
        }
        goodsService.saveOrUpdate(goods);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Goods> jobLambdaQueryWrapper = Wrappers.<Goods>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            jobLambdaQueryWrapper.like(Goods::getGoodsName,search);
        }
//        排序
        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = jobLambdaQueryWrapper.orderByDesc(Goods::getGoodsPubdate);
        IPage<Goods> page = goodsService.page(new Page<>(pageNum,pageSize),goodsLambdaQueryWrapper);
        return Result.success(page);

//        Page<User> userPage = goodsService.findPage(new Page<>(pageNum, pageSize), search);
//        return Result.success(userPage);
    }

    @GetMapping("/signal")
    public Result<?> findSignalPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = "") String author){

        LambdaQueryWrapper<Goods> jobLambdaQueryWrapper = Wrappers.<Goods>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            jobLambdaQueryWrapper.like(Goods::getGoodsName,search);
        }
        jobLambdaQueryWrapper.like(Goods::getAuthor,author);
        IPage<Goods> page = goodsService.page(new Page<>(pageNum,pageSize),jobLambdaQueryWrapper);
        return Result.success(page);

//        Page<Goods> page = goodsService.findPage(new Page<>(pageNum, pageSize), search);
//        return Result.success(page);
    }

    @DeleteMapping("/{goodsId}")
    public Result<?> delMajorById(@PathVariable Long goodsId){
        goodsService.removeById(goodsId);
        return Result.success();
    }

    @GetMapping("/getVO/{goodsId}")
    public Result<?> getGoodsVO(@PathVariable int goodsId){
        GoodsVO goodsVO = goodsService.getGoodsVO(goodsId);
        return Result.success(goodsVO);
    }
}
