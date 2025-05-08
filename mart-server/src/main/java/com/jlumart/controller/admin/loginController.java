package com.jlumart.controller.admin;

import com.jlumart.utils.JwtUtil;
import com.jlumart.entity.Employee;
import com.jlumart.result.Result;
import com.jlumart.service.EmployeeService;
import com.jlumart.interceptor.utils.JwtRedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlumart.properties.JwtProperties;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/employee")
public class loginController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtRedisUtil jwtRedisUtil;
    @PostMapping("/login")
    public Result login(@RequestBody Employee employee) {
        log.info("员工登录：{}", employee);
        Employee e = employeeService.login(employee);


        //登录成功,生成令牌,下发令牌
        if (e != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("name", e.getName());
            claims.put("username", e.getUsername());

            String jwt = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims); //jwt包含了当前登录的员工信息
            jwtRedisUtil.saveJwtToRedis(e.getId(), jwt, jwtProperties.getAdminTtl());
            return Result.success(jwt);
        }

        //登录失败, 返回错误信息
        return Result.error("用户名或密码错误");
    }

}
