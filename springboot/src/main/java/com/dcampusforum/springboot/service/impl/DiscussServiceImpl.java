package com.dcampusforum.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcampusforum.springboot.entity.Discuss;
import com.dcampusforum.springboot.mapper.DiscussMapper;
import com.dcampusforum.springboot.service.DiscussService;
import com.dcampusforum.springboot.vo.DiscussVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shd
 * @since 2022-05-07
 */
@Service
public class DiscussServiceImpl extends ServiceImpl<DiscussMapper, Discuss> implements DiscussService {
    @Resource
    DiscussMapper discussMapper;
    @Override
    public List<DiscussVO> getListVO(String belong) {
        return discussMapper.getListVO(belong);
    }
}
