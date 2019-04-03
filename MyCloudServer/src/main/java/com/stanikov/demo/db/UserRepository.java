package com.stanikov.demo.db;

import com.stanikov.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAll();
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
