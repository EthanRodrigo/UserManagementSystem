package com.ethan.system.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/errors")
@RequiredArgsConstructor
public class AccessDeniedController {

    @GetMapping("/accessDenied")
    public ResponseEntity<String> accessDenied() throws AccessDeniedException {
        throw new AccessDeniedException("Access Denied");
    }
}
