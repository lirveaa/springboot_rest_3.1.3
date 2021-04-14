package com.lirvess.spring_boot.springboot_rest.controller;

import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserDao userService;


    //Закоментировал, потому что просто выводится текст /admin/start
   /*@GetMapping("start" )
    public String startAdmin(ModelMap modelMap) {
        List<User> userList = userService.findAll();
        modelMap.addAttribute("listUsers", userList);

        return "/admin/start";
    }*/


    @GetMapping("start")
    public ModelAndView index(ModelAndView modelAndView){
        List<User> userList = userService.findAll();
        modelAndView.addObject("listUsers", userList );
        modelAndView.setViewName("admin/start");
        return modelAndView;
    }

    @GetMapping(value = "/new")
    public ModelAndView addNewUserForm(ModelAndView modelAndView) {
        getNewModelAndView(modelAndView);
        modelAndView.addObject("isAdmin", false);
        modelAndView.addObject("isUser", true);
        modelAndView.setViewName("admin/add_new");
        return modelAndView;
    }

    @PostMapping("/new")
    public String newUser(@RequestParam(name="isAdmin", required = false) boolean isAdmin,
                          @RequestParam(name="isUser", required = false) boolean isUser,
                          @ModelAttribute User user) {
        Set<Role> rolesToAdd = new HashSet<>();
        if (isUser) {
            rolesToAdd.add(new Role(1L, "ROLE_USER"));
        }
        if (isAdmin) {
            rolesToAdd.add(new Role(2L, "ROLE_ADMIN"));
        }
        user.setRoles(rolesToAdd);
        userService.save(user);
        return "redirect:/admin/start";
    }

    @GetMapping("/edit")
    public ModelAndView editForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        ModelAndView mav = new ModelAndView("admin/update");
        //User user = userService.readUser(id);
        User user = userService.findById(id).orElseThrow();
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(name = "id", defaultValue = "1") long id,
                           @ModelAttribute User user) {
        Set<Role>  setOldRoles = userService.findById(id).orElseThrow().getRoles();
        user.setRoles(setOldRoles);
        //userService.updateUser(user);
        userService.save(user);
        return "redirect:/admin/start";
    }

    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        //userService.deleteUser(id);
        userService.deleteById(id);
        return "redirect:/admin/start";
    }

    @GetMapping("/search")
    public String findUserByIdForm() {
        return "/admin/search_form";
    }

    @PostMapping("/searchResult")
    public ModelAndView findUserResultForm(@RequestParam(name = "id", defaultValue = "1") long id,
                                           ModelAndView mav) {
        //User user = userService.readUser(id);
        User user = userService.findById(id).orElseThrow();
        mav.setViewName("/admin/search_result_form");
        mav.addObject("user", user);
        return mav;
    }

    private void getNewModelAndView(ModelAndView modelAndView) {
        User user = new User();
        user.setUsername("somebody");
        user.setPassword("password");
        System.out.println(user);
        //List<Role> listRoles = userService.rolesList();
        Set<Role> listRoles = Role.getRolesSet();
        modelAndView.addObject("roles", listRoles);
        modelAndView.addObject("user", user);
    }
}
