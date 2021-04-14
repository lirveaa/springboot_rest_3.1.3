package com.lirvess.spring_boot.springboot_rest.dao;

import com.lirvess.spring_boot.springboot_rest.model.Role;
import com.lirvess.spring_boot.springboot_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserDao  extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.username =:username")
    User getUserByUsername(@Param("username") String username);
}
