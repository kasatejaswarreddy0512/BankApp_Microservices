package com.ktsr.service;

import com.ktsr.dto.JwtResponse;
import com.ktsr.dto.SignInRequest;
import com.ktsr.dto.SignUpRequest;
import com.ktsr.entity.User;
import com.ktsr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User registerUser(SignUpRequest signUpRequest) {

        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setAadhaarNumber(signUpRequest.getAadhaarNumber());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setAddress(signUpRequest.getAddress());
        user.setPanNumber(signUpRequest.getPanNumber());
        user.setProfilePictureUrl(signUpRequest.getProfilePictureUrl());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setRole(signUpRequest.getRole());
        return userRepository.save(user);
    }

    public JwtResponse longinUser(SignInRequest signInRequest){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                signInRequest.getEmail(),
                                signInRequest.getPassword()));

                var user = userRepository.findByEmail(signInRequest.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                var jwtToken = jwtService.generateToken(user);

                JwtResponse jwtResponse = new JwtResponse();
                jwtResponse.setToken(jwtToken);
                return jwtResponse;
    }

    public User getUserProfile(String jwt) {
        String email = extractEmailFromJwt(jwt);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

    }

    public User updateUserProfile(Long id, SignUpRequest signUpRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setAadhaarNumber(signUpRequest.getAadhaarNumber());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setAddress(signUpRequest.getAddress());
        user.setPanNumber(signUpRequest.getPanNumber());
        user.setProfilePictureUrl(signUpRequest.getProfilePictureUrl());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setRole(signUpRequest.getRole());

        return userRepository.save(user);
    }

    private String extractEmailFromJwt(String jwt) {
        return jwtService.extractUsername(jwt);
    }


}
