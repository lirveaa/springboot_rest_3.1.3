package com.lirvess.spring_boot.springboot_rest.controller;

import com.lirvess.spring_boot.springboot_rest.model.User;
import com.lirvess.spring_boot.springboot_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")

public class RestController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> userList(){
        return userService.findAll();
    }

    @GetMapping("user{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        try{
            User user = userService.findById(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users", consumes ="application/json", produces = "application/json")
    public void addUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id){
        try{
            User userFromDB = userService.findById(id);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public void removeUser(@PathVariable Long id){
        userService.deleteById(id);
    }

}
