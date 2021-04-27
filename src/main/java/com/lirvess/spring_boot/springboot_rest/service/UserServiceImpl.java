package com.lirvess.spring_boot.springboot_rest.service;

import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow();
    }

    @Override
    public User getUserByUsername(String login) {
        return userDao.getUserByUsername(login);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userDao.getUserByEmail(user.getEmail());
        if(userFromDB != null){
            return false;
        }
        if(user.getRoles().size()==0){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(new Role(1L,"ROLE_USER")));
        }

        userDao.save(user);
        return true;
    }

    @Override
    public User saveNewUser(User user) {
        User userFromDB = userDao.getUserByEmail(user.getEmail());
        if (userFromDB != null) {
            System.out.println("NS Error:  We have same user by Email!");
            return null;
        }
        if (user.getRoles().size() == 0) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }

        return userDao.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        if(user.getRoles().size()==0){
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        userDao.save(user);
        return true;
    }
}
