package com.jlumart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItem {
    private Long id;
    private Long quantity;
    private Double price;
    private String  name;
    private String image;
}
