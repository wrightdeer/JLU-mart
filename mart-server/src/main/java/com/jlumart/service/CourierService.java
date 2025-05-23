package com.jlumart.service;

import com.jlumart.dto.CourierDTO;
import com.jlumart.dto.CourierPageDTO;
import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.result.PageResult;
import com.jlumart.vo.CourierInfoVO;
import com.jlumart.vo.CourierLoginVO;
import com.jlumart.vo.CourierVO;
import com.jlumart.vo.CourierViewVO;

public interface CourierService {
    void add(CourierDTO courierDTO);

    PageResult page(CourierPageDTO courierPageDTO);

    CourierVO getById(Long id);

    void update(CourierDTO courierDTO);

    void updateStatus(Long id, Integer status);

    void delete(Long id);

    CourierInfoVO getInfo();

    CourierLoginVO login(LoginDTO loginDTO);

    void editPassword(PasswordDTO passwordDTO);

    CourierViewVO getViewById(Long id);
}
