package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 充值金额
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountDTO {
    private Double amount;
}
