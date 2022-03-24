package com.dcampusforum.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcampusforum.springboot.entity.User;
import com.dcampusforum.springboot.mapper.UserMapper;
import com.dcampusforum.springboot.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
//    @Resource
//    UserMapper userMapper;
//    @Override
//    public Page<User> findPage(Page<Object> objectPage, String search) {
//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//        if (StrUtil.isNotBlank(search)) {
//            wrapper.like(User::getUserName, search);
//        }
//        Page<User> userPage = userMapper.findPage(objectPage, search);
//        return userPage;
//
//    }
//
//    @Override
//    public Page<User> findGoodsPage(Page<Object> objectPage, String search) {
//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//        if (StrUtil.isNotBlank(search)) {
//            wrapper.like(User::getUserName, search);
//        }
//        Page<User> userPage = userMapper.findPage(objectPage, search);
//        return userPage;
//    }
}
