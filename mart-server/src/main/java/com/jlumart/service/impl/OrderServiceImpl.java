package com.jlumart.service.impl;

import com.jlumart.mapper.OrderMapper;
import com.jlumart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
}
