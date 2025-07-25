package com.ktsr.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String aadhaarNumber;
    private String phoneNumber;
    private String address;
    private String panNumber;
    private String profilePictureUrl;
    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Role role; // e.g., "USER", "ADMIN"
}
