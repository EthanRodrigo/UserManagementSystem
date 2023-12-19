package com.ethan.system.users.controller;

import com.ethan.system.users.model.User;
import com.ethan.system.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;
    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello User");
    }

    /* A user can access his own profile
    * */
    @GetMapping("/getUser")
    private ResponseEntity<User> getAUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(repository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("No user found"))
        );
    }
}
