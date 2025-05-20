package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增/修改地址簿
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressBookDTO {
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
}
