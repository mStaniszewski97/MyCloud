package com.stanikov.demo.api;

import com.stanikov.demo.db.UserRepository;
import com.stanikov.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {
    private UserRepository userRepository;
    private PasswordEncoder test;
    private User loggedUser;
    private boolean isLogged;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        test = new BCryptPasswordEncoder();
        isLogged = false;
        loggedUser = null;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody User user) {
        if (checkIfUserIsUnique(user.getUsername(), user.getEmail())) {
            String password = test.encode(user.getPassword());
            user.setPassword(password);
            userRepository.save(user);
            System.out.println("reg completed");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    private boolean checkIfUserIsUnique(String username, String email) {
        User userByUsername = userRepository.findUserByUsername(username);
        User userByEmail = userRepository.findUserByEmail(email);
        if (userByUsername == null && userByEmail == null) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody User user) {
        User userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail != null && test.matches(user.getPassword(), userByEmail.getPassword())) {
            System.out.println("Log in");
            isLogged = true;
            loggedUser = userByEmail;
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(value = "/isLogged", method = RequestMethod.GET)
    public Boolean isLogged() {
        if (loggedUser != null) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout() {
        isLogged = false;
        loggedUser = null;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
