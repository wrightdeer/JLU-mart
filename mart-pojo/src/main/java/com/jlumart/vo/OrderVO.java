package com.jlumart.vo;

import com.jlumart.entity.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
/**
 * 订单详情
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {
    /**
     * 取消原因
     */
    private String cancelReason;
    /**
     * 配送员
     */
    private String courierName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 距离
     */
    private Long deliveryDistance;
    /**
     * 配送费
     */
    private Double deliveryFee;
    /**
     * 送达时间
     */
    private LocalDateTime deliveryTime;
    /**
     * 预计送达时间
     */
    private LocalDateTime estimatedDeliveryTime;
    /**
     * 订单id
     */
    private Long id;
    /**
     * 商品列表
     */
    private List<ProductItem> items;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 详细收货地址
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
     * 收货人性别，1 男 2 女
     */
    private Integer receiverSex;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态，0 未支付 1 待配送 2 配送中 3 已完成 4 已取消
     */
    private Integer status;
    /**
     * 总额
     */
    private Double totalAmount;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;

}
