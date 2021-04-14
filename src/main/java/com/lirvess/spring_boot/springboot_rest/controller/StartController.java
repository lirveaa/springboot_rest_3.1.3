package com.lirvess.spring_boot.springboot_rest.controller;


import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class StartController {

    private static boolean isInit = false;

    @Autowired
    private UserDao userService;

    @GetMapping("/")
    public String indexPage() {
        return "redirect: /login";
    }

    @GetMapping("hello")
    public String printWelcome(ModelMap model, Principal principal) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, " + principal.getName() + "!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("Spring Boot version ");
        model.addAttribute("messages", messages);


        return "hello";
    }

    private void insertDataToDatabase() {
        System.out.println("\nInserting data ....");
        Role roleUser = new Role(1L, "ROLE_USER");
        Role roleAdmin = new Role(2L, "ROLE_ADMIN");
        Set<Role> bothSet = new HashSet<Role>();
        bothSet.add(roleUser);
        bothSet.add(roleAdmin);
        Set<Role> admSet = new HashSet<>();
        admSet.add(roleAdmin);
        Set<Role> userSet = new HashSet<>();
        userSet.add(roleUser);

        User marlo = new User("admin", "admin", 1955, admSet);
        User john = new User("user", "user", 1960, userSet);
        User jackson = new User("boss", "boss", 1965, bothSet);
        User jagger = new User("Ben", "Johnson", 1956, admSet);
        User tiger = new User("Laura", "Crawford", 1970, bothSet);

        userService.save(marlo);
        userService.save(john);
        userService.save(jackson);
        userService.save(jagger);
        userService.save(tiger);
    }






}
