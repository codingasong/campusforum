package com.dcampusforum.springboot.vo;

import com.dcampusforum.springboot.entity.Discuss;
import com.dcampusforum.springboot.entity.Posts;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shd
 * @since 2022-05-07
 */
public class DiscussVO extends Discuss {
    @Getter
    @Setter
    private String gender;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String major;
    @Getter
    @Setter
    private Integer age;
}
