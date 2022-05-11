package com.dcampusforum.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dcampusforum.springboot.entity.Posts;
import com.dcampusforum.springboot.mapper.PostsMapper;
import com.dcampusforum.springboot.vo.PostsVO;

public interface PostsService extends IService<Posts> {
    PostsVO getPostsVO(Integer postsId);
}
