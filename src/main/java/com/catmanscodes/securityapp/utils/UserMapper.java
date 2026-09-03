package com.catmanscodes.securityapp.utils;

import com.catmanscodes.securityapp.dto.UserDto;
import com.catmanscodes.securityapp.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapToUserDto(User user) {

        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getIsActive(),
                user.getRole()
        );
    }

    public User mapToUser(UserDto userDto) {

        return new User(
                userDto.id(),
                userDto.userName(),
                userDto.email(),
                userDto.password(),
                userDto.isActive(),
                userDto.role()
        );
    }

}
