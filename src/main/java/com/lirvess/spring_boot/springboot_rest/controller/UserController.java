package com.lirvess.spring_boot.springboot_rest.controller;


import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userService;

    @GetMapping("info")
    public ModelAndView index(Principal principal, ModelAndView modelAndView) {
        User user = userService.getUserByUsername(principal.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/info");
        return modelAndView;
    }

}
