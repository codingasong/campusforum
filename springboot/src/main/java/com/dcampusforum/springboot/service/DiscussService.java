package com.dcampusforum.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dcampusforum.springboot.entity.Discuss;
import com.dcampusforum.springboot.vo.DiscussVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shd
 * @since 2022-05-07
 */
public interface DiscussService extends IService<Discuss> {
    List<DiscussVO> getListVO(String belong);
}
