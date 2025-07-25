package com.ktsr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String accountNumber;
    private String IfscCode;
    private String accountType;
    private String branchName;
    private Double balance;

    private Long userId; // Foreign key to User entity
}
