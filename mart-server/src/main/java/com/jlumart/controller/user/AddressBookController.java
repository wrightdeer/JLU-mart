package com.jlumart.controller.user;

import com.jlumart.dto.AddressBookDTO;
import com.jlumart.result.Result;
import com.jlumart.service.AddressBookService;
import com.jlumart.vo.AddressBookVO;
import com.jlumart.vo.DeliveryRangeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("addressBookController")
@Slf4j
@RequestMapping("/user/addressBook")
@Api(tags = "地址簿相关接口")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    @ApiOperation(value = "获取地址簿")
    public Result<List<AddressBookVO>> list() {
        log.info("获取地址簿");
        List<AddressBookVO> addressBookVOList = addressBookService.list();
        return Result.success(addressBookVOList);
    }
    @PostMapping
    @ApiOperation(value = "新增地址簿")
    public Result add(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("新增地址簿，地址簿信息：{}", addressBookDTO);
        addressBookService.add(addressBookDTO);
        return Result.success();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改地址簿")
    public Result edit(@RequestBody AddressBookDTO addressBookDTO, @PathVariable Long id) {
        log.info("修改地址簿，地址簿信息：{}", addressBookDTO);
        addressBookDTO.setId(id);
        addressBookService.edit(addressBookDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除地址簿")
    public Result delete(@PathVariable Long id) {
        log.info("删除地址簿，地址簿id：{}", id);
        addressBookService.delete(id);
        return Result.success();
    }

    @PutMapping("/default/{id}")
    @ApiOperation(value = "设置默认地址")
    public Result setDefault(@PathVariable Long id) {
        log.info("设置默认地址，地址簿id：{}", id);
        addressBookService.setDefault(id);
        return Result.success();
    }

    @GetMapping("/default")
    @ApiOperation(value = "获取默认地址")
    public Result<AddressBookVO> getDefault() {
        log.info("获取默认地址");
        AddressBookVO addressBookVO = addressBookService.getDefault();
        return Result.success(addressBookVO);
    }

    @GetMapping("/deliveryRange/{id}")
    @ApiOperation(value = "获取地址簿的配送范围信息")
    public Result<DeliveryRangeVO> getDeliveryRange(@PathVariable Long id) {
        log.info("获取地址簿的配送范围信息，地址簿id：{}", id);
        DeliveryRangeVO deliveryRange = addressBookService.getDeliveryRange(id);
        return Result.success(deliveryRange);
    }
}
