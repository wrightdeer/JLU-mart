package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingOrder {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单状态
     * 0-未支付 1-待配送 2-配送中 3-已完成 4-已取消
     */
    private Integer status;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人性别
     * 1-男 2-女
     */
    private Integer receiverGender;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 详细收货地址
     */
    private String receiverAddress;

    /**
     * 商品列表详情（JSON格式）
     * 包含商品id、名字、图片url、价格、数量等信息
     */
    private String items;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 订单总额
     */
    private Double totalAmount;

    /**
     * 配送费
     */
    private Double deliveryFee;

    /**
     * 配送距离（单位：米）
     */
    private Long deliveryDistance;

    /**
     * 预计送达时间
     */
    private LocalDateTime estimatedDeliveryTime;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 配送员ID
     */
    private Long courierId;

    /**
     * 配送员昵称
     */
    private String courierName;

    /**
     * 实际送达时间
     */
    private LocalDateTime deliveryTime;
}
