package com.dcampusforum.springboot.vo;

import com.dcampusforum.springboot.entity.Job;
import com.dcampusforum.springboot.entity.Posts;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shd
 * @since 2022-05-07
 */
public class JobVO extends Job {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String avatar;
}
