package com.example.walletwise.Auth.dtos;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
