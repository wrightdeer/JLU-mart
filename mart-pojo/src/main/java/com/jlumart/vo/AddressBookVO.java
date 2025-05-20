package com.jlumart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * id查询地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookVO {
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
}
