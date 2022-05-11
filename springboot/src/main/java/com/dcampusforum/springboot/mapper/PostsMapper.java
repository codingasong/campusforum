package com.dcampusforum.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcampusforum.springboot.entity.Posts;
import com.dcampusforum.springboot.vo.GoodsVO;
import com.dcampusforum.springboot.vo.PostsVO;
import org.apache.ibatis.annotations.Select;

public interface PostsMapper extends BaseMapper<Posts> {
    @Select("SELECT p.*, u.email,u.avatar FROM posts p, USER u  WHERE p.author = u.user_name AND p.posts_id= #{postsId}")
    PostsVO getPostsVO(Integer postsId);
}
