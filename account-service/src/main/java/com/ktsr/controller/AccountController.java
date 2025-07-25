package com.ktsr.controller;

import com.ktsr.dto.UserDto;
import com.ktsr.enity.Account;
import com.ktsr.feign.UserService;
import com.ktsr.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;


    @PostMapping("/create/{userId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId,
                                                 @RequestBody Account account,
                                                 @RequestHeader("Authorization") String authHeader) {
        String jwt= authHeader.substring(7).trim();

        ResponseEntity<UserDto> userResponse = userService.getUserProfile("Bearer " + jwt);

        UserDto userDto= userResponse.getBody();

        if(userDto == null ) {
           throw new RuntimeException("User not found");
        }

        Account createdAccount = accountService.createAccount(userId, account, String.valueOf(userDto.getRole()));
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId, @RequestHeader("Authorization") String authHeader) {
        String jwt=authHeader.replace("Bearer","").trim();
        UserDto userDto=userService.getUserProfile("Bearer "+jwt).getBody();
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId,
                                                 @RequestBody Account account,
                                                 @RequestHeader("Authorization") String authHeader) {
        String jwt=authHeader.replace("Bearer","").trim();
        UserDto userDto=userService.getUserProfile("Bearer "+jwt).getBody();
        assert userDto != null;
        Account updatedAccount = accountService.updateAccount(accountId, account, String.valueOf(userDto.getRole()));
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long accountId, @RequestHeader("Authorization") String authHeader) {
        String jwt=authHeader.replace("Bearer","").trim();
        UserDto userDto=userService.getUserProfile("Bearer "+jwt).getBody();
        assert userDto != null;
        return ResponseEntity.ok(accountService.deleteAccount(accountId, String.valueOf(userDto.getRole())));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String authHeader) {
        String jwt=authHeader.replace("Bearer","").trim();
        UserDto userDto=userService.getUserProfile("Bearer "+jwt).getBody();
        assert userDto != null;
        return ResponseEntity.ok(accountService.getAccountByUserId(userId));
    }


    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber, @RequestHeader("Authorization") String authHeader) {
        String jwt=authHeader.replace("Bearer","").trim();
        UserDto userDto=userService.getUserProfile("Bearer "+jwt).getBody();
        assert userDto != null;
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

    @PutMapping("/updateBalance/{accountId}")
    public ResponseEntity<Account> updateAccountBalance(@PathVariable Long accountId,
                                                        @RequestParam Double amount,
                                                        @RequestHeader("Authorization") String authHeader) {
        String jwt=authHeader.replace("Bearer","").trim();
        UserDto userDto=userService.getUserProfile("Bearer "+jwt).getBody();
        assert userDto != null;
        return ResponseEntity.ok(accountService.updateAccountBalance(accountId, amount));
    }
}
