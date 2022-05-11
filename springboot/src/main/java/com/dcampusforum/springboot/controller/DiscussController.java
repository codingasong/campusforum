package com.dcampusforum.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.common.Result;
import com.dcampusforum.springboot.entity.Discuss;
import com.dcampusforum.springboot.entity.Goods;
import com.dcampusforum.springboot.service.DiscussService;
import com.dcampusforum.springboot.service.GoodsService;
import com.dcampusforum.springboot.vo.DiscussVO;
import com.dcampusforum.springboot.vo.GoodsVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author shd
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/discuss")
public class DiscussController {
    @Resource
    DiscussService discussService;


    @PostMapping
    public Result<?> add(@RequestBody Discuss discuss) {
        if(discuss.getPubdate() == null) {
            discuss.setPubdate(new Date());//设置时间
        }

        discussService.saveOrUpdate(discuss);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Discuss> jobLambdaQueryWrapper = Wrappers.<Discuss>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            jobLambdaQueryWrapper.like(Discuss::getBelong,search);
        }
        IPage<Discuss> page = discussService.page(new Page<>(pageNum,pageSize),jobLambdaQueryWrapper);
        return Result.success(page);

//        Page<User> userPage = goodsService.findPage(new Page<>(pageNum, pageSize), search);
//        return Result.success(userPage);
    }

    @GetMapping("/getAll")
    public Result<?> getAll(){
        return Result.success(discussService.list());
    }



    @DeleteMapping("/{id}")
    public Result<?> delMajorById(@PathVariable Long id){
        discussService.removeById(id);
        return Result.success();
    }


    @GetMapping("/getVO/{belong}")
    public Result<?> getVO(@PathVariable String belong){
        List<DiscussVO> discussVO = discussService.getListVO(belong);
        return Result.success(discussVO);
    }


}
