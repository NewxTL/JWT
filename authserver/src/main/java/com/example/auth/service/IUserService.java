package com.example.auth.service;

import com.example.auth.entity.User;

public interface IUserService {
    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User getUserByUserName(String username);
}
