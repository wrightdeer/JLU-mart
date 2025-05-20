package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrder {
    private Long id;
    private String orderId;
    private Long  userId;
    private Double amount;
    private Integer status;
    private LocalDateTime createTime;
}
