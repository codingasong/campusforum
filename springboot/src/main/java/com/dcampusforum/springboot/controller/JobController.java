package com.dcampusforum.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.common.Result;
import com.dcampusforum.springboot.entity.Goods;
import com.dcampusforum.springboot.entity.Job;
import com.dcampusforum.springboot.entity.Major;
import com.dcampusforum.springboot.service.JobService;
import com.dcampusforum.springboot.service.MajorService;
import com.dcampusforum.springboot.vo.JobVO;
import com.dcampusforum.springboot.vo.PostsVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/job")
public class JobController {
    @Resource
    JobService jobService;


    @PostMapping
    public Result<?> addMajor(@RequestBody Job job) {
        if(job.getJobPubdate() == null) {
            job.setJobPubdate(new Date()); //设置时间
        }
        jobService.saveOrUpdate(job);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<Job> jobLambdaQueryWrapper = Wrappers.<Job>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            jobLambdaQueryWrapper.like(Job::getJobName,search);
        }
        IPage<Job> page = jobService.page(new Page<>(pageNum,pageSize),jobLambdaQueryWrapper);
        return Result.success(page);
    }

    @GetMapping("/signal")
    public Result<?> findSignalPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = "") String author){

        LambdaQueryWrapper<Job> jobLambdaQueryWrapper = Wrappers.<Job>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            jobLambdaQueryWrapper.like(Job::getJobName,search);
        }
        jobLambdaQueryWrapper.like(Job::getAuthor,author);
        jobLambdaQueryWrapper.orderByDesc(Job::getJobPubdate);

        IPage<Job> page = jobService.page(new Page<>(pageNum,pageSize),jobLambdaQueryWrapper);
        return Result.success(page);
    }

    @DeleteMapping("/{jobId}")
    public Result<?> delMajorById(@PathVariable Long jobId){
        jobService.removeById(jobId);
        return Result.success();
    }

    /**
     *
     * @param jobId
     * @return
     */
    @GetMapping("/getVO/{jobId}")
    public Result<?> getGoodsVO(@PathVariable int jobId){
        JobVO jobVO = jobService.getVO(jobId);
        return Result.success(jobVO);
    }
}
