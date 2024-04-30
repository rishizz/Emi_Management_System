package com.cognizant.controller;

import com.cognizant.dto.UserDTO;
import com.cognizant.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUserDTO = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        UserDTO userDTO1 = userService.loginUser(userDTO.getUsername(), userDTO.getPassword());
        if(userDTO1 != null){
            return ResponseEntity.ok(userDTO1);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
