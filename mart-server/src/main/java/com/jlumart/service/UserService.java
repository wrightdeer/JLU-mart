package com.jlumart.service;

import com.jlumart.dto.LoginDTO;
import com.jlumart.dto.PasswordDTO;
import com.jlumart.dto.UserDTO;
import com.jlumart.dto.UserRegisterDTO;
import com.jlumart.vo.UserInfoVO;
import com.jlumart.vo.UserLoginVO;
import com.jlumart.vo.UserVO;

public interface UserService {
    UserInfoVO getInfo();

    UserLoginVO register(UserRegisterDTO userRegisterDTO);

    UserLoginVO login(LoginDTO loginDTO);

    void editPassword(PasswordDTO passwordDTO);

    UserVO getById();

    void editUser(UserDTO userDTO);
}
