package com.dcampusforum.springboot.vo;

import com.dcampusforum.springboot.entity.Goods;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shd
 * @since 2022-04-27
 */
public class GoodsVO extends Goods {
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String email;
}
