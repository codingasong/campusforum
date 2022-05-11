package com.dcampusforum.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcampusforum.springboot.entity.Job;
import com.dcampusforum.springboot.vo.JobVO;
import com.dcampusforum.springboot.vo.PostsVO;
import org.apache.ibatis.annotations.Select;

public interface JobMapper extends BaseMapper<Job> {
    @Select("SELECT j.*, u.email,u.avatar FROM job j, USER u WHERE j.author = u.user_name AND j.job_id= #{job_id}")
    JobVO getVO(Integer jobId);
}
