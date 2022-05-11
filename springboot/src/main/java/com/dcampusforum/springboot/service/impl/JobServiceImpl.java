package com.dcampusforum.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcampusforum.springboot.entity.Job;
import com.dcampusforum.springboot.mapper.JobMapper;
import com.dcampusforum.springboot.service.JobService;
import com.dcampusforum.springboot.vo.JobVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    @Resource
    JobMapper jobMapper;
    @Override
    public JobVO getVO(Integer jobId) {
        return jobMapper.getVO(jobId);
    }
}
