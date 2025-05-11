package com.jlumart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPageDTO {
    private Integer page;
    private Integer pageSize;
    private Integer status;
    private String name;
    private Long categoryId;
}
