package com.dcampusforum.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcampusforum.springboot.entity.Job;
import com.dcampusforum.springboot.mapper.JobMapper;
import com.dcampusforum.springboot.service.JobService;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
}
