package com.dcampusforum.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcampusforum.springboot.entity.Discuss;
import com.dcampusforum.springboot.vo.DiscussVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author shd
 * @since 2022-05-07
 */
public interface DiscussMapper extends BaseMapper<Discuss> {
    @Select("SELECT d.*,u.gender,u.email,u.major,u.age FROM discuss d, user u WHERE d.user_name = u.user_name AND d.belong = #{belong}")
    List<DiscussVO> getListVO(String belong);
}
