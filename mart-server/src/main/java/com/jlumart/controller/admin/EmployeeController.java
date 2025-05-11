package com.jlumart.controller.admin;

import com.jlumart.dto.EmployeeDTO;
import com.jlumart.dto.EmployeePageDTO;
import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.entity.Employee;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.EmployeeService;
import com.jlumart.vo.EmployeeInfoVO;
import com.jlumart.vo.EmployeeLoginVO;
import com.jlumart.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 员工管理
 */
@RestController("adminEmployeeController")
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<EmployeeLoginVO> login(@RequestBody LoginDTO loginDTO) {
        log.info("员工登录。账号密码：{}", loginDTO);
        EmployeeLoginVO employee = employeeService.login(loginDTO);
        return Result.success(employee);
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取员工信息")
    public Result<EmployeeInfoVO> info() {
        log.info("根据令牌查询员工信息。");
        EmployeeInfoVO employeeInfo = employeeService.getInfo();
        return Result.success(employeeInfo);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public Result logout() {
        log.info("员工退出登录");
        return Result.success();
    }

    @PutMapping("/editPassword")
    @ApiOperation(value = "修改密码")
    public Result editPassword(@RequestBody PasswordDTO passwordDTO) {
        log.info("修改密码：{}", passwordDTO);
        employeeService.editPassword(passwordDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询员工")
    public Result<PageResult> page(EmployeePageDTO employeePageDTO) {
        log.info("分页查询员工，分页参数：{}", employeePageDTO);
        PageResult pageResult = employeeService.page(employeePageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "id查询员工")
    public Result<EmployeeVO> getById(@PathVariable Long id) {
        log.info("根据id查询员工：{}", id);
        EmployeeVO employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改员工")
    public Result editEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
        log.info("修改员工，员工信息：{}", employee);
        employee.setId(id);
        employeeService.editEmployee(employee);
        return Result.success();
    }

    @PostMapping
    @ApiOperation(value = "新增员工")
    public Result addEmployee(@RequestBody EmployeeDTO  employee) {
        log.info("新增员工，员工信息：{}", employee);
        employeeService.addEmployee(employee);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除员工")
    public Result delete(@PathVariable Long id) {
        log.info("删除员工：{}", id);
        employeeService.delete(id);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    @ApiOperation(value = "修改员工状态")
    public Result editStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("修改员工状态，员工id：{}，状态：{}", id, status);
        employeeService.editStatus(id, status);
        return Result.success();
    }

}
