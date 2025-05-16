package com.jlumart.service.impl;

import com.jlumart.constant.JwtClaimsConstant;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.dto.UserDTO;
import com.jlumart.dto.UserRegisterDTO;
import com.jlumart.entity.User;
import com.jlumart.exception.AccountNotFoundException;
import com.jlumart.exception.PasswordErrorException;
import com.jlumart.interceptor.utils.JwtRedisUtil;
import com.jlumart.mapper.UserMapper;
import com.jlumart.properties.JwtProperties;
import com.jlumart.service.UserService;
import com.jlumart.utils.JwtUtil;
import com.jlumart.vo.UserInfoVO;
import com.jlumart.vo.UserLoginVO;
import com.jlumart.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtRedisUtil jwtRedisUtil;

    final static String DEFAULT_AVATAR_URL = "https://web-lyt-jlumart.oss-cn-beijing.aliyuncs.com/default-avatar.jpg";

    public UserInfoVO getInfo() {
        Long id = BaseContext.getCurrentId();
        return userMapper.getInfo(id);
    }

    @Transactional
    public UserLoginVO register(UserRegisterDTO userRegisterDTO) {
        LocalDateTime  now = LocalDateTime.now();
        User user  = User.builder()
                .username(userRegisterDTO.getUsername())
                .name(userRegisterDTO.getName())
                .password(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()))
                .avatar(DEFAULT_AVATAR_URL)
                .createTime(now)
                .updateTime(now)
                .lastRequestTime(now)
                .build();
        userMapper.insert(user);

        // 创建令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        String userId = "USER:" + user.getId();
        jwtRedisUtil.saveJwtToRedis(userId, token, jwtProperties.getUserTtl() / 1000);
        return UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .avatar(user.getAvatar())
                .token(token)
                .build();
    }

    @Transactional
    public UserLoginVO login(LoginDTO loginDTO) {
        // 根据用户名查询用户
        User user = userMapper.getByUsername(loginDTO.getUsername());
        // 判断账号是否存在、密码是否正确
        if (user == null) {
            throw new AccountNotFoundException();
        }
        String password = loginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!user.getPassword().equals(password)) {
            throw new PasswordErrorException();
        }
        // 更新登录次数
        user.setLoginCount(1L);
        userMapper.update(user);
        // 创建令牌，存入redis，返回
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        String userId = "USER:" + user.getId();
        jwtRedisUtil.saveJwtToRedis(userId, token, jwtProperties.getUserTtl() / 1000);
        return UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .avatar(user.getAvatar())
                .token(token)
                .build();
    }

    public void editPassword(PasswordDTO passwordDTO) {
        String oldPassword = DigestUtils.md5DigestAsHex(passwordDTO.getOldPassword().getBytes());
        String newPassword = DigestUtils.md5DigestAsHex(passwordDTO.getNewPassword().getBytes());
        Long id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);
        if (!user.getPassword().equals(oldPassword)) {
            throw new PasswordErrorException();
        }
        user.setPassword(newPassword);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);

        // 先清除旧token
        String userId = "USER:" + BaseContext.getCurrentId();
        jwtRedisUtil.removeJwtFromRedis(userId);
        // 将本次的token存入redis
        String jwt = BaseContext.getCurrentJwt();
        jwtRedisUtil.saveJwtToRedis(userId, jwt, jwtProperties.getAdminTtl() / 1000);
    }

    public UserVO getById() {
        Long id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);
        if (user != null) {
            return UserVO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .avatar(user.getAvatar())
                    .sex(user.getSex())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .balance(user.getBalance())
                    .build();
        }
        return null;
    }

    public void editUser(UserDTO userDTO) {
        Long  id = BaseContext.getCurrentId();
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

}
