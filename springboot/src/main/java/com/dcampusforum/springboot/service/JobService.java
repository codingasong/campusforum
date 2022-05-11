package com.dcampusforum.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dcampusforum.springboot.entity.Job;
import com.dcampusforum.springboot.vo.GoodsVO;
import com.dcampusforum.springboot.vo.JobVO;

public interface JobService extends IService<Job> {
    JobVO getVO(Integer jobId);
}
