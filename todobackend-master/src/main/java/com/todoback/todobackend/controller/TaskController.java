package com.todoback.todobackend.controller;

import com.todoback.todobackend.domain.AuthenticationDTO;
import com.todoback.todobackend.domain.RegisterDTO;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
    @RestController
public class TaskController {

        @Autowired
        UserService userService;

        @PostMapping("/user/authenticate")
        public AuthenticationDTO authenticateUser(@RequestBody User user) {
            return userService.authenticateUser(user);
        }

        @PostMapping("/user/register")
        public void registerUser(@RequestBody RegisterDTO registerDTO) {
            userService.registerUser(registerDTO);
        }

        @GetMapping("/user/activation/{activationId}")
        public RedirectView activateUser(@PathVariable String activationId) {
            userService.activateUser(activationId);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("http://localhost:4200/activated");
            return redirectView;
        }

        @GetMapping("/user/id/{username}")
        public int prepareUserId(@PathVariable String username) {
            return userService.prepareUserIdFromUsername(username);
        }

        @GetMapping("/user/{username}")
        public User getUserData(@PathVariable String username) {
            return userService.getUserDataByUsername(username);
        }

    }
