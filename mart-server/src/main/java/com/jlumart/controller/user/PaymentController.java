package com.jlumart.controller.user;

import com.jlumart.dto.AmountDTO;
import com.jlumart.dto.PaymentDTO;
import com.jlumart.dto.OrderPageDTO;
import com.jlumart.result.PageResult;
import com.jlumart.result.Result;
import com.jlumart.service.PaymentService;
import com.jlumart.vo.PaymentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userPaymentController")
@Api(value = "用户充值相关接口")
@RequestMapping("/user/payment")
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/recharge")
    @ApiOperation(value = "充值请求")
    public Result<PaymentVO> recharge(@RequestBody AmountDTO amountDTO) {
        log.info("充值请求,参数:{}", amountDTO);
        PaymentVO paymentVO = paymentService.recharge(amountDTO.getAmount());
        return Result.success(paymentVO);
    }

    @PostMapping("/confirmRecharge")
    @ApiOperation(value = "充值确认")
    public Result confirmRecharge(@RequestBody PaymentDTO paymentDTO) {
        log.info("充值确认,参数:{}", paymentDTO);
        paymentService.confirmRecharge(paymentDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "充值记录分页")
    public Result<PageResult> page(OrderPageDTO orderPageDTO) {
        log.info("充值记录分页,参数:{}", orderPageDTO);
        PageResult pageResult = paymentService.page(orderPageDTO);
        return Result.success(pageResult);
    }
}
