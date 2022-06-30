package com.example.demo.service;

import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-02-12
 */
public interface IUserService extends IService<User> {


    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
}
