package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工分页参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageDTO {
    /**
     * 当前页码
     */
    private Integer page;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 员工昵称
     */
    private String name;
}
