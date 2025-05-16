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
public class AddressBook {
    /**
     * 市
     */
    private String city;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 区
     */
    private String district;
    /**
     * 地址簿id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 是否为默认地址，0 否 1 是
     */
    private Integer isDefault;
    /**
     * 标签，1 家 2 学校 3 公司
     */
    private Integer label;
    /**
     * 收货人电话
     */
    private String phone;
    /**
     * 省
     */
    private String province;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 性别 1男 2 女
     */
    private Integer sex;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
