package com.lirvess.spring_boot.springboot_rest.controller;

import com.lirvess.spring_boot.springboot_rest.model.User;
import com.lirvess.spring_boot.springboot_rest.service.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class UserController {

    private final UserDetailsServiceImpl userService;

    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("user")
    public ModelAndView index(Principal principal, ModelAndView modelAndView) {
        User user = userService.getUserByEmail(principal.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/user");
        return modelAndView;
    }

}
