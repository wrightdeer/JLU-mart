package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 充值/购物订单分页参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPageDTO {
    /**
     * 充值订单号
     */
    private String orderId;
    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页展示数量
     */
    private Integer pageSize;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 配送员id
     */
    private Long courierId;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
