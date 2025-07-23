package com.ktsr.controller;

import com.ktsr.dto.SignUpRequest;
import com.ktsr.entity.User;
import com.ktsr.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final AuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        String jwt=authHeader.substring(7).trim();
        return ResponseEntity.ok(authService.getUserProfile(jwt));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.updateUserProfile(id, signUpRequest));
    }
}
