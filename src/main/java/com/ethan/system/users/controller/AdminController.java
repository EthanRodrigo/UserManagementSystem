package com.ethan.system.users.controller;

import com.ethan.system.users.model.User;
import com.ethan.system.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository repository;
    @GetMapping("/hello")
    private ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello Admin");
    }
    @GetMapping("/listUsers")
    private ResponseEntity<List<User>> listAllUsers(){
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping("/getUser")
    private ResponseEntity<User> getAUser(@RequestParam String email){
        return ResponseEntity.ok(repository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("No user found"))
        );
    }
}
