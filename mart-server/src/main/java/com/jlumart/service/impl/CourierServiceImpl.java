package com.jlumart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jlumart.constant.JwtClaimsConstant;
import com.jlumart.constant.PasswordConstant;
import com.jlumart.constant.StatusConstant;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.CourierDTO;
import com.jlumart.dto.CourierPageDTO;
import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.entity.Courier;
import com.jlumart.exception.AccountLockedException;
import com.jlumart.exception.AccountNotFoundException;
import com.jlumart.exception.AuthorizationException;
import com.jlumart.exception.PasswordErrorException;
import com.jlumart.interceptor.utils.JwtRedisUtil;
import com.jlumart.mapper.CourierMapper;
import com.jlumart.properties.JwtProperties;
import com.jlumart.result.PageResult;
import com.jlumart.service.CourierService;
import com.jlumart.utils.JwtUtil;
import com.jlumart.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierMapper courierMapper;
    @Autowired
    private JwtRedisUtil jwtRedisUtil;
    @Autowired
    private JwtProperties jwtProperties;

    public void add(CourierDTO courierDTO) {
        Courier courier = new Courier();
        BeanUtils.copyProperties(courierDTO, courier);
        courier.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        courier.setCreateTime(LocalDateTime.now());
        courier.setUpdateTime(LocalDateTime.now());
        courierMapper.insert(courier);
    }

    public PageResult page(CourierPageDTO courierPageDTO) {
        PageHelper.startPage(courierPageDTO.getPage(), courierPageDTO.getPageSize());
        Page<CourierPageVO> page = courierMapper.page(courierPageDTO);
        return new PageResult(page.getTotal(), page);
    }

    public CourierVO getById(Long id) {
        Courier courier = courierMapper.getById(id);
        if (courier != null) {
            CourierVO courierVO = new CourierVO();
            BeanUtils.copyProperties(courier, courierVO);
            return courierVO;
        }
        return null;
    }

    public void update(CourierDTO courierDTO) {
        Courier courier = new Courier();
        BeanUtils.copyProperties(courierDTO, courier);
        courier.setUpdateTime(LocalDateTime.now());
        courierMapper.update(courier);
    }

    public void updateStatus(Long id, Integer status) {
        Courier courier = new Courier();
        courier.setId(id);
        courier.setStatus(status);
        courierMapper.update(courier);
        if (Objects.equals(status, StatusConstant.DISABLE)) {
            String userId = "COURIER:" + id;
            jwtRedisUtil.removeJwtFromRedis(userId);
        }
    }

    public void delete(Long id) {
        Long currentId = BaseContext.getCurrentId();
        if (currentId != 1) {
            throw new AuthorizationException();
        }
        courierMapper.delete(id);
        String userId = "COURIER:" + id;
        jwtRedisUtil.removeJwtFromRedis(userId);
    }

    public CourierInfoVO getInfo() {
        Courier courier = courierMapper.getById(BaseContext.getCurrentId());
        if (courier != null) {
            CourierInfoVO courierInfoVO = new CourierInfoVO();
            BeanUtils.copyProperties(courier, courierInfoVO);
            return courierInfoVO;
        }
        return null;
    }

    public CourierLoginVO login(LoginDTO loginDTO) {
        Courier courier = courierMapper.getByUsername(loginDTO.getUsername());
        if (courier == null) {
            throw new AccountNotFoundException();
        }
        if (courier.getStatus().equals(StatusConstant.DISABLE)) {
            throw new AccountLockedException();
        }
        String password = loginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!courier.getPassword().equals(password)) {
            throw new PasswordErrorException();
        }
        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.COURIER_ID, courier.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getCourierSecretKey(),
                jwtProperties.getCourierTtl(),
                claims);
        String userId = "COURIER:" + courier.getId();
        jwtRedisUtil.saveJwtToRedis(userId, token, jwtProperties.getCourierTtl() / 1000);
        return CourierLoginVO.builder()
                .id(courier.getId())
                .username(courier.getUsername())
                .name(courier.getName())
                .avatar(courier.getAvatar())
                .token(token)
                .build();
    }

    public void editPassword(PasswordDTO passwordDTO) {
        String oldPassword = DigestUtils.md5DigestAsHex(passwordDTO.getOldPassword().getBytes());
        String newPassword = DigestUtils.md5DigestAsHex(passwordDTO.getNewPassword().getBytes());
        Long id = BaseContext.getCurrentId();
        Courier courier = courierMapper.getById(id);
        if (!courier.getPassword().equals(oldPassword)) {
            throw new PasswordErrorException();
        }
        courier.setPassword(newPassword);
        courier.setUpdateTime(LocalDateTime.now());
        courierMapper.update(courier);

        // 先清除旧token
        String userId = "COURIER:" + BaseContext.getCurrentId();
        jwtRedisUtil.removeJwtFromRedis(userId);
        // 将本次的token存入redis
        String jwt = BaseContext.getCurrentJwt();
        jwtRedisUtil.saveJwtToRedis(userId, jwt, jwtProperties.getCourierTtl() / 1000);
    }

    public CourierViewVO getViewById(Long id) {
        Courier courier = courierMapper.getById(id);
        if (courier != null) {
            CourierViewVO courierViewVO = new CourierViewVO();
            BeanUtils.copyProperties(courier, courierViewVO);
            return courierViewVO;
        }
        return null;
    }

}
