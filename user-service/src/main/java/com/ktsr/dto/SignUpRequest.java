package com.ktsr.dto;

import com.ktsr.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String aadhaarNumber; // 🔥 Lowercase 'a'
    private String phoneNumber;
    private String address;
    private String panNumber;
    private String profilePictureUrl;
    private String dateOfBirth;

    private Role role; // e.g., "USER", "ADMIN"
}

