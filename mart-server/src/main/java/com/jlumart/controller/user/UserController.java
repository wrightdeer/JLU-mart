package com.jlumart.controller.user;

import com.jlumart.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userController")
@Slf4j
@RequestMapping("/user/user")
@Api(tags = "用户接口")
public class UserController {
    @Autowired
    private UserService userService;
}
