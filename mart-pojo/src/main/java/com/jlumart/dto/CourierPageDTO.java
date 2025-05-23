package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配送员分页查询
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierPageDTO {
    /**
     * 配送员昵称
     */
    private String name;
    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页展示数量
     */
    private Integer pageSize;
}
