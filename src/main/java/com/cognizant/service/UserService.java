package com.cognizant.service;

import com.cognizant.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO loginUser(String username,String password);

}
