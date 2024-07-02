package com.dkf.ODAD.auth;

import com.dkf.ODAD.auth.dto.AuthJwtResponse;
import com.dkf.ODAD.auth.dto.AuthLoginRequest;
import com.dkf.ODAD.auth.dto.AuthRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/hello")
    public String hello(){
        return "La nube está funcionando!";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthJwtResponse> register(@RequestBody AuthRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthJwtResponse> login(@RequestBody AuthLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/create")
    public ResponseEntity<AuthJwtResponse> createAdmin(@RequestBody AuthRegisterRequest request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

}
