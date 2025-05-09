package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageDTO {
    private Integer page;
    private Integer pageSize;
    private String name;
}
