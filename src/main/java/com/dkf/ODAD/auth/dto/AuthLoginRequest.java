package com.dkf.ODAD.auth.dto;

import lombok.Data;

@Data
public class AuthLoginRequest {
    public String email;
    public String password;
}
