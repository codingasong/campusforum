package com.dcampusforum.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcampusforum.springboot.entity.Posts;
import com.dcampusforum.springboot.mapper.PostsMapper;
import com.dcampusforum.springboot.service.PostsService;
import org.springframework.stereotype.Service;

@Service

public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {
}
