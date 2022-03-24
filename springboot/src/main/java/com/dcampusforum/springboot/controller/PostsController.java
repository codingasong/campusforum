package com.dcampusforum.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.common.Result;
import com.dcampusforum.springboot.entity.Job;
import com.dcampusforum.springboot.entity.Posts;
import com.dcampusforum.springboot.entity.User;
import com.dcampusforum.springboot.service.PostsService;
import com.dcampusforum.springboot.service.UserService;
import com.dcampusforum.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

//返回json数据
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Resource
    PostsService postsService;

//    增加或修改
    @PostMapping
    public Result<?> addPosts(@RequestBody Posts posts) {
        if(posts.getPostsPubdate() == null) {
            posts.setPostsPubdate(new Date()); //设置时间
        }
//        posts.setPostsPubdate(new Date());

        postsService.saveOrUpdate(posts);
        return Result.success();
    }

//    删除
    @DeleteMapping("/{postsId}")
    public Result<?> delPostsById(@PathVariable Long postsId){
        postsService.removeById(postsId);
        return Result.success();
    }

//    通过id查询
    @GetMapping("/{postsId}")
    public Result<?> getByPostsId(@PathVariable Long postsId){
        Posts posts = postsService.getById(postsId);
        return Result.success(posts);
    }

//    分页查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Posts> postsLambdaQueryWrapper = Wrappers.<Posts>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            postsLambdaQueryWrapper.like(Posts::getPostsTitle,search);

        }
        IPage<Posts> page = postsService.page(new Page<>(pageNum,pageSize),postsLambdaQueryWrapper);
        return Result.success(page);
    }

    @GetMapping("/signal")
    public Result<?> findSignalPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = "") String author){

        LambdaQueryWrapper<Posts> jobLambdaQueryWrapper = Wrappers.<Posts>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            jobLambdaQueryWrapper.like(Posts::getPostsTitle,search);
        }
        jobLambdaQueryWrapper.like(Posts::getAuthor,author);
        IPage<Posts> page = postsService.page(new Page<>(pageNum,pageSize),jobLambdaQueryWrapper);
        return Result.success(page);
    }





}
