package com.cognizant.service.impl;


import com.cognizant.dto.UserDTO;
import com.cognizant.entities.User;
import com.cognizant.exceptions.InvalidPasswordException;
import com.cognizant.exceptions.UserNameIsAlreadyPresentException;
import com.cognizant.exceptions.UsernameNotFoundException;
import com.cognizant.repositories.UserRepository;
import com.cognizant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User checkUser = userRepository.findByUsername(userDTO.getUsername());
        if(checkUser!=null){
            throw new UserNameIsAlreadyPresentException("username is already register");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = new UserDTO();
        savedUserDTO.setUsername(savedUser.getUsername());
        savedUserDTO.setRole(savedUser.getRole());
        return savedUserDTO;


    }
    @Override
    public UserDTO loginUser(String username, String password){
        User user = userRepository.findByUsername(username);

        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
            throw new UsernameNotFoundException("User Not found");
        }
        System.out.println(user.getPassword()+password+"Password");
        if(!user.getPassword().equals(password)){
            throw new InvalidPasswordException("Invalid password");
        }

//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Invalid password");
//        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;


    }

}

