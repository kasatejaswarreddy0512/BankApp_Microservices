package com.ktsr.feign;


import com.ktsr.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserServiceClient {

    @GetMapping("/api/user/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String authHeader);

}
