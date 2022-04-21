package com.dcampusforum.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcampusforum.springboot.common.Result;
import com.dcampusforum.springboot.entity.User;
import com.dcampusforum.springboot.mapper.UserMapper;
import com.dcampusforum.springboot.service.UserService;
import com.dcampusforum.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//返回json数据
@RestController
@RequestMapping("/user")
public class UserController {

    //    UserMapper userMapper;
    @Resource
    UserService userService;
//    @Resource
//    UserMapper userMapper;
//    更新用
//    @PutMapping

    @PostMapping
    public Result<?> addUser(@RequestBody User user) {
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        if(user.getRole() == null){
            user.setRole(2);
        }
        userService.saveOrUpdate(user);
        return Result.success();
    }
@GetMapping("/{userId}")
    public Result<?> getById(@PathVariable Long userId){
        return Result.success(userService.getById(userId));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            userLambdaQueryWrapper.like(User::getUserName,search);
        }
        IPage<User> page = userService.page(new Page<>(pageNum,pageSize),userLambdaQueryWrapper);
        return Result.success(page);

//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//        if (StrUtil.isNotBlank(search)) {
//            wrapper.like(User::getUserName, search);
//        }
//        Page<User> userPage = userService.findPage(new Page<>(pageNum, pageSize), search);
//        return Result.success(userPage);

//        Page<User> page = userService.page(new Page<>(pageNum,pageSize),userLambdaQueryWrapper);
//        page(IPage<?> page, Wrapper<T> queryWrapper);
    }

//    @GetMapping("/{userId}")
//    public Result<?> findGoodsPage(@RequestParam(defaultValue = "1") Integer pageNum,
//                              @RequestParam(defaultValue = "10") Integer pageSize,
//                              @RequestParam(defaultValue = "") String search){
//        Page<User> userPage = userService.findPage(new Page<>(pageNum, pageSize), search);
//        return Result.success(userPage);
//    }

    @DeleteMapping("/{userId}")
    public Result<?> delUserById(@PathVariable Long userId){
        userService.removeById(userId);
        return Result.success();
    }


    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        queryWrapper.eq("password", user.getPassword());
        User selectuser = userService.getOne(queryWrapper);
//        User selectuser = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()).eq(User::getPassword,user.getPassword()));

        if(selectuser == null){
            return Result.error("-1","用户名或密码错误");
        }
        // 生成token
        String token = TokenUtils.genToken(selectuser);
        selectuser.setToken(token);
        return Result.success(selectuser);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {

        User selectuser = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()));
        if(selectuser != null){
            return Result.error("-1","用户名已存在");
        }
        user.setGender("男");
        user.setAge(18);
        user.setRole(2);
        user.setMajor("计算机科学与技术");
        user.setAvatar("http://localhost:8888/files/56d1e9f1d4194a2792d27a385c52297e");
        userService.save(user);
        return Result.success();
    }


    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }
}
