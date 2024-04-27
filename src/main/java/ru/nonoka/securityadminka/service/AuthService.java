package ru.nonoka.securityadminka.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.nonoka.securityadminka.dto.AppError;
import ru.nonoka.securityadminka.dto.JwtRequest;
import ru.nonoka.securityadminka.dto.JwtResponse;
import ru.nonoka.securityadminka.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    JwtTokenUtils jwtTokenUtils;
    AdminService adminService;
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> authorization(JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), "Неправильный логин или пароль"), HttpStatus.FORBIDDEN);
        }
        UserDetails userDetails = adminService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtils.createToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
