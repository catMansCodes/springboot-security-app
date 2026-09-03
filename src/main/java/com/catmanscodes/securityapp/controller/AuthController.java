package com.catmanscodes.securityapp.controller;

import com.catmanscodes.securityapp.dto.AuthRequestDto;
import com.catmanscodes.securityapp.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequestDto authRequestDto) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.userName(),
                        authRequestDto.password()
                )
        );

        if (authenticate.isAuthenticated()) {

            String role = authenticate.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority().replace("ROLE_", "");

            return jwtService.generateToken(authRequestDto.userName(), role);
        }

        return "Authentication failed";
    }
}
