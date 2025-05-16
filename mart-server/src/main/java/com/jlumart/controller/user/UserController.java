package com.jlumart.controller.user;

import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.dto.UserDTO;
import com.jlumart.dto.UserRegisterDTO;
import com.jlumart.result.Result;
import com.jlumart.service.UserService;
import com.jlumart.vo.UserInfoVO;
import com.jlumart.vo.UserLoginVO;
import com.jlumart.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userController")
@Slf4j
@RequestMapping("/user/user")
@Api(tags = "用户接口")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息")
    public Result<UserInfoVO> info() {
        log.info("获取用户信息。");
        UserInfoVO userInfo = userService.getInfo();
        return Result.success(userInfo);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result<UserLoginVO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册: {}", userRegisterDTO);
        UserLoginVO userLoginVO =userService.register(userRegisterDTO);
        return Result.success(userLoginVO);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<UserLoginVO> login(@RequestBody LoginDTO loginDTO) {
        log.info("用户登录: {}", loginDTO);
        UserLoginVO userLoginVO = userService.login(loginDTO);
        return Result.success(userLoginVO);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户登出")
    public Result logout() {
        log.info("用户登出");
        return Result.success();
    }

    @PutMapping("/editPassword")
    @ApiOperation(value = "修改密码")
    public Result editPassword(@RequestBody PasswordDTO passwordDTO) {
        log.info("修改密码: {}", passwordDTO);
        userService.editPassword(passwordDTO);
        return Result.success();
    }

    @GetMapping
    @ApiOperation(value = "获取用户信息")
    public Result<UserVO> getById(){
        log.info("获取用户信息");
        UserVO userVO = userService.getById();
        return Result.success(userVO);
    }

    @PutMapping
    @ApiOperation(value = "修改用户信息")
    public Result editUser(@RequestBody UserDTO userDTO){
        log.info("修改用户信息: {}", userDTO);
        userService.editUser(userDTO);
        return Result.success();
    }
}
