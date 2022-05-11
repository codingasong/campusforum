package com.dcampusforum.springboot.vo;

import com.dcampusforum.springboot.entity.Posts;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shd
 * @since 2022-05-07
 */
public class PostsVO extends Posts {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String avatar;
}
