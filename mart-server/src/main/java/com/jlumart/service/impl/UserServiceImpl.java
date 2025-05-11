package com.jlumart.service.impl;

import com.jlumart.mapper.UserMapper;
import com.jlumart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
}
