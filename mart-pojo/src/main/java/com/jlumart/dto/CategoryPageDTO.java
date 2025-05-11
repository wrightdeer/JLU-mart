package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPageDTO {
    private Integer page;
    private Integer pageSize;
    private String name;
    private Integer status;
    private Long parentId;
}
