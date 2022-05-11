package com.dcampusforum.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcampusforum.springboot.entity.Posts;
import com.dcampusforum.springboot.mapper.PostsMapper;
import com.dcampusforum.springboot.service.PostsService;
import com.dcampusforum.springboot.vo.PostsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {
    @Resource
    PostsMapper postsMapper;
    @Override
    public PostsVO getPostsVO(Integer postsId) {
        return postsMapper.getPostsVO(postsId);
    }
}
