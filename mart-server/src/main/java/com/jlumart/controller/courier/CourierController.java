package com.jlumart.controller.courier;

import com.jlumart.context.BaseContext;
import com.jlumart.dto.CourierDTO;
import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.result.Result;
import com.jlumart.service.CourierService;
import com.jlumart.vo.CourierInfoVO;
import com.jlumart.vo.CourierLoginVO;
import com.jlumart.vo.CourierViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("courierController")
@Slf4j
@RequestMapping("/courier/courier")
@Api(tags = "配送员相关接口")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @GetMapping("/info")
    @ApiOperation(value = "查询配送员信息")
    public Result<CourierInfoVO> info() {
        log.info("查询配送员信息。");
        CourierInfoVO courierInfo = courierService.getInfo();
        return Result.success(courierInfo);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<CourierLoginVO> login(@RequestBody LoginDTO loginDTO) {
        log.info("配送员登录。账号密码：{}", loginDTO);
        CourierLoginVO courierLoginVO = courierService.login(loginDTO);
        return Result.success(courierLoginVO);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public Result logout() {
        log.info("配送员退出登录。");
        return Result.success();
    }

    @PutMapping("/editPassword")
    @ApiOperation(value = "修改密码")
    public Result editPassword(@RequestBody PasswordDTO passwordDTO) {
        log.info("修改密码。新密码：{}", passwordDTO);
        courierService.editPassword(passwordDTO);
        return Result.success();
    }

    @GetMapping
    @ApiOperation(value = "查询配送员信息")
    public Result<CourierViewVO> getInfo() {
        log.info("查询配送员信息。");
        Long id = BaseContext.getCurrentId();
        CourierViewVO courierViewInfo = courierService.getViewById(id);
        return Result.success(courierViewInfo);
    }
    @PutMapping
    @ApiOperation(value = "修改配送员信息")
    public Result editCourier(@RequestBody CourierDTO courierDTO) {
        log.info("修改配送员信息。");
        courierDTO.setId(BaseContext.getCurrentId());
        courierService.update(courierDTO);
        return Result.success();
    }
}
