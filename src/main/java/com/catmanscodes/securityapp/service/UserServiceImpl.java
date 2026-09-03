package com.catmanscodes.securityapp.service;

import com.catmanscodes.securityapp.dto.UserDto;
import com.catmanscodes.securityapp.entity.User;
import com.catmanscodes.securityapp.exception.UserNotFoundedException;
import com.catmanscodes.securityapp.repository.UserRepository;
import com.catmanscodes.securityapp.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = userMapper.mapToUser(userDto);

        user.setIsActive(Boolean.TRUE);
        user.setRole(userDto.role());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return userMapper.mapToUserDto(user);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        System.out.println("hi.........");
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundedException("User not found with username: " + userName));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(user.getRole()))
                .build();
    }
}
