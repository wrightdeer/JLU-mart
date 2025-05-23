package com.jlumart.controller.admin;

import com.jlumart.dto.CourierDTO;
import com.jlumart.dto.CourierPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.CourierService;
import com.jlumart.vo.CourierVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminCourierController")
@Slf4j
@RequestMapping("/admin/courier")
@Api(tags = "配送员管理")
public class CourierController {
    @Autowired
    private CourierService courierService;

    @PostMapping
    @ApiOperation(value = "添加配送员")
    public Result add(@RequestBody CourierDTO courierDTO   ) {
        log.info("添加配送员，配送员信息：{}", courierDTO);
        courierService.add(courierDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询配送员")
    public Result<PageResult> page(CourierPageDTO courierPageDTO) {
        log.info("分页查询配送员，参数：{}", courierPageDTO);
        PageResult pageResult = courierService.page(courierPageDTO);
        return Result.success(pageResult);
    }
    
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询配送员")
    public Result<CourierVO> getById(@PathVariable Long id) {
        log.info("根据id查询配送员，id：{}", id);
        CourierVO courierVO = courierService.getById(id);
        return Result.success(courierVO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改配送员信息")
    public Result update(@PathVariable Long id, @RequestBody CourierDTO courierDTO) {
        log.info("修改配送员信息，id：{}, 配送员信息：{}", id, courierDTO);
        courierDTO.setId(id);
        courierService.update(courierDTO);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    @ApiOperation(value = "修改配送员状态")
    public Result updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        log.info("修改配送员状态，id：{}, 状态：{}", id, status);
        courierService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除配送员")
    public Result delete(@PathVariable Long id) {
        log.info("删除配送员，id：{}", id);
        courierService.delete(id);
        return Result.success();
    }
}
