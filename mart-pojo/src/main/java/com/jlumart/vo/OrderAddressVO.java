package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单地址
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddressVO {
    /**
     * 收货人地址
     */
    private String receiverAddress;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 收货人性别
     */
    private Integer receiverSex;
}
