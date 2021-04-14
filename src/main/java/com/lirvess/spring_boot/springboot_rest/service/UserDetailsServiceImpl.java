package com.lirvess.spring_boot.springboot_rest.service;

import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userService;

    public UserDetailsServiceImpl(){
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        //User user = userService.getUser(name);
        User user = userService.getUserByUsername(name);
        if(user ==null){
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(),
                        roles);
        return userDetails;
    }
}
