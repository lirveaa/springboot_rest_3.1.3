package com.lirvess.spring_boot.springboot_rest.service;

import com.lirvess.spring_boot.springboot_rest.dao.UserDao;
import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl {

    private final  UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(Long id){
        return userDao.findById(id).orElseThrow();
    }

    public User getUserByUsername(String login) {
        return userDao.getUserByUsername(login);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userDao.getUserByEmail(user.getEmail());
        if (userFromDB != null) {
            return false;
        }
        if (user.getRoles().size() == 0) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        userDao.save(user);
        return true;
    }

    public boolean updateUser(User user) {

        if (user.getRoles().size() == 0) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        userDao.save(user);
        return true;
    }

}
