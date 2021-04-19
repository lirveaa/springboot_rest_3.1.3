package com.lirvess.spring_boot.springboot_rest.controller;

import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;
import com.lirvess.spring_boot.springboot_rest.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    public UserDetailsServiceImpl userService;

    public AdminController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin" )
    public String startAdmin(ModelMap modelMap) {
        Iterable<User> usersList = userService.findAll();
        Set<Role>rolesList = Role.getRolesSet();
        modelMap.addAttribute("usersList", usersList);
        modelMap.addAttribute("roles",rolesList);
        //List<User> userList = userService.findAll();
        //modelMap.addAttribute("listUsers", userList);
        return "admin";
    }

    @PostMapping("/create")
    public String createUser(HttpServletRequest request,
                             @RequestParam(name = "isAdmin",required = false)boolean isAdmin,
                             @RequestParam(name ="isUser", required = false)boolean isUser){
        User user = new User();
        user.setLogin(request.getParameter("newName").trim());
        user.setLastName(request.getParameter("newLastName").trim());
        user.setAge(Integer.parseInt(request.getParameter("newAge")));
        user.setEmail(request.getParameter("newEmail").trim());
        user.setPassword(request.getParameter("newPassword").trim());
        Set<Role> rolesToAdd = new HashSet<>();
        if (isUser) {
            rolesToAdd.add(new Role(1L, "ROLE_USER"));
        }
        if (isAdmin) {
            rolesToAdd.add(new Role(2L, "ROLE_ADMIN"));
        }
        user.setRoles(rolesToAdd);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        //userService.deleteUser(id);
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping(value = "/edit")
    public String saveUserMapping(ModelMap model,
                                  @RequestParam Long id,
                                  @RequestParam Integer age,
                                  @RequestParam String first_name,
                                  @RequestParam String last_name,
                                  @RequestParam String password,
                                  @RequestParam String email,
                                  @RequestParam(value = "newRoles", required = false) long[]roles){
        User user = userService.findById(id);
        user.setPassword(password);
        user.setLogin(first_name);
        user.setLastName(last_name);
        user.setEmail(email);
        user.setAge(age);
        userService.updateUser(user);
        return "redirect:/admin";
    }

}
