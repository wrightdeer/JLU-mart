package com.jlumart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jlumart.constant.JwtClaimsConstant;
import com.jlumart.constant.PasswordConstant;
import com.jlumart.constant.StatusConstant;
import com.jlumart.context.BaseContext;
import com.jlumart.dto.EmployeeDTO;
import com.jlumart.dto.EmployeePageDTO;
import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.entity.Employee;
import com.jlumart.exception.*;
import com.jlumart.interceptor.utils.JwtRedisUtil;
import com.jlumart.mapper.EmployeeMapper;
import com.jlumart.properties.JwtProperties;
import com.jlumart.result.PageResult;
import com.jlumart.service.EmployeeService;
import com.jlumart.utils.JwtUtil;
import com.jlumart.vo.EmployeeInfoVO;
import com.jlumart.vo.EmployeeLoginVO;
import com.jlumart.vo.EmployeePageVO;
import com.jlumart.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtRedisUtil jwtRedisUtil;
    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long  id = BaseContext.getCurrentId();
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        employeeMapper.insert(employee);


    }

    public EmployeeInfoVO getInfo() {
        Long id = BaseContext.getCurrentId();
        Employee employee = employeeMapper.getById(id);
        if (employee != null) {
            return EmployeeInfoVO.builder()
                    .id(employee.getId())
                    .username(employee.getUsername())
                    .name(employee.getName())
                    .avatar(employee.getAvatar())
                    .build();
        }
        throw new AccountNotFoundException();
    }

    public void editPassword(PasswordDTO passwordDTO) {
        String oldPassword = DigestUtils.md5DigestAsHex(passwordDTO.getOldPassword().getBytes());
        String newPassword = DigestUtils.md5DigestAsHex(passwordDTO.getNewPassword().getBytes());
        Long id = BaseContext.getCurrentId();
        Employee employee = employeeMapper.getById(id);
        if (!employee.getPassword().equals(oldPassword)) {
            throw new PasswordErrorException();
        }
        employee.setPassword(newPassword);
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);

        // 先清除旧token
        String userId = "ADMIN:" + BaseContext.getCurrentId();
        jwtRedisUtil.removeJwtFromRedis(userId);
        // 将本次的token存入redis
        String jwt = BaseContext.getCurrentJwt();
        jwtRedisUtil.saveJwtToRedis(userId, jwt, jwtProperties.getAdminTtl() / 1000);
    }

    public PageResult page(EmployeePageDTO employeePageDTO) {
        PageHelper.startPage(employeePageDTO.getPage(), employeePageDTO.getPageSize());
        Page<EmployeePageVO> page = employeeMapper.page(employeePageDTO);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    public EmployeeVO getById(Long id) {
        if (id != null) {
            Employee employee = employeeMapper.getById(id);
            return EmployeeVO.builder()
                    .avatar(employee.getAvatar())
                    .username(employee.getUsername())
                    .name(employee.getName())
                    .phone(employee.getPhone())
                    .sex(employee.getSex())
                    .email(employee.getEmail())
                    .build();
        }
        return null;
    }

    public void editEmployee(EmployeeDTO employeeDTO) {
        Long id = BaseContext.getCurrentId();
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(id);
        employeeMapper.update(employee);
    }

    public void delete(Long id) {
        Long currentId = BaseContext.getCurrentId();
        if (currentId != 1) {
            throw new AuthorizationException();
        }
        if (id == 1) {
            throw new ForbiddenOperationException("最高管理员无法被删除");
        }
        employeeMapper.delete(id);
        String userId = "ADMIN:" + id;
        jwtRedisUtil.removeJwtFromRedis(userId);
    }

    public void editStatus(Long id, Integer status) {
        Long currentId = BaseContext.getCurrentId();
        if (id == 1) {
            throw new ForbiddenOperationException("最高管理员状态无法被修改");
        }
        if (Objects.equals(currentId, id)) {
            throw new IllegalOperationException("无法修改自己的状态");
        }
        Employee employee = new Employee();
        employee.setId(id);
        employee.setStatus(status);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(currentId);
        employeeMapper.update(employee);

        if (Objects.equals(status, StatusConstant.DISABLE)) {
            String userId = "ADMIN:" + id;
            jwtRedisUtil.removeJwtFromRedis(userId);
        }
    }

    @Override
    public EmployeeLoginVO login(LoginDTO loginDTO){
        Employee employee = employeeMapper.getByUsername(loginDTO.getUsername());
        if (employee == null) {
            throw new AccountNotFoundException();
        }
        if (employee.getStatus().equals(StatusConstant.DISABLE)) {
            throw new AccountLockedException();
        }

        String password = loginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!employee.getPassword().equals(password)) {
            throw new PasswordErrorException();
        }
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        String userId = "ADMIN:" + employee.getId();
        jwtRedisUtil.saveJwtToRedis(userId, token, jwtProperties.getAdminTtl() / 1000);

        return EmployeeLoginVO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .avatar(employee.getAvatar())
                .token(token)
                .build();
    }


}
