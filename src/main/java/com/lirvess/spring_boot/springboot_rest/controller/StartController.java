package com.lirvess.spring_boot.springboot_rest.controller;


import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;
import com.lirvess.spring_boot.springboot_rest.service.UserDetailsServiceImpl;
import com.lirvess.spring_boot.springboot_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class StartController {

    private UserService userService;

    public StartController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public  String getLoginWithEmail(@RequestParam(value = "error", required = false) String error,
                                     @RequestParam(value = "logout",required = false)String logout,
                                     Model model){
        model.addAttribute("error", error!=null);
        model.addAttribute("logout", logout!= null);
        return "login_page";
    }

    @GetMapping("hello")
    public String printWelcome() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userRoles = auth.getAuthorities().toString();
        if(userRoles.contains("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        return "redirect:/user";
    }

    @GetMapping("/admin")
    public String startAdmin(ModelMap modelMap) {
        Iterable<User> userList = userService.findAll();
        Set<Role> rolesList = Role.getRolesSet();
        modelMap.addAttribute("usersList", userList);
        modelMap.addAttribute("roles", rolesList);
        return "admin";
    }

    @GetMapping("user")
    public ModelAndView index(Principal principal, ModelAndView modelAndView) {
        User user = userService.getUserByEmail(principal.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/user");
        return modelAndView;
    }



}
