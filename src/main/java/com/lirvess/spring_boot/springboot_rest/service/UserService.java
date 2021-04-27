package com.lirvess.spring_boot.springboot_rest.service;

import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User getUserByUsername(String login);
    User getUserByEmail(String email);
    void deleteById(Long id);
    List<User> findAll();
    boolean saveUser(User user);
    User saveNewUser(User user);
    boolean updateUser(User user);

}
