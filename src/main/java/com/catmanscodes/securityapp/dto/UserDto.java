package com.catmanscodes.securityapp.dto;

public record UserDto(
        Long id,
        String userName,
        String email,
        String password,
        Boolean isActive,
        String role
) {
}
