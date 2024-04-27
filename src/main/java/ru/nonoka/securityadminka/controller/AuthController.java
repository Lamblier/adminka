package ru.nonoka.securityadminka.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nonoka.securityadminka.dto.JwtRequest;
import ru.nonoka.securityadminka.service.AuthService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/authorization")
    public ResponseEntity<?> authorization(@RequestBody JwtRequest jwtRequest) {
        return authService.authorization(jwtRequest);
    }

    @GetMapping("/admin")
    public String testRequest() {
        return "Admin data";
    }
}
