package com.dcampusforum.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.common.Result;
import com.dcampusforum.springboot.entity.Major;
import com.dcampusforum.springboot.service.MajorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/major")
public class MajorController {
    @Resource
    MajorService majorService;

//    更新用
//    @PutMapping

    @PostMapping
    public Result<?> addMajor(@RequestBody Major major) {
        Major one = majorService.getOne(Wrappers.<Major>lambdaQuery().eq(Major::getMajorName, major.getMajorName()));
        if(one != null) {
            return Result.error("-1","该专业已存在");
        }
        majorService.saveOrUpdate(major);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Major> majorLambdaQueryWrapper = Wrappers.<Major>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            majorLambdaQueryWrapper.like(Major::getMajorName,search);
        }
        IPage<Major> page = majorService.page(new Page<>(pageNum,pageSize),majorLambdaQueryWrapper);
        return Result.success(page);
    }

    @DeleteMapping("/{majorId}")
    public Result<?> delMajorById(@PathVariable Long majorId){
        majorService.removeById(majorId);
        return Result.success();
    }


}
