package com.catmanscodes.securityapp.config;

import com.catmanscodes.securityapp.security.JWTSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTSecurityFilter jwtSecurityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/v1/users/create",
                                        "/api/v1/auth/authenticate")
                                .permitAll()

                                .requestMatchers("/api/v1/room/view/{id}")
                                .hasAnyRole("ADMIN", "STAFF", "GUEST")

                                .requestMatchers("/api/v1/room/view/**")
                                .hasAnyRole("ADMIN", "STAFF")

                                .requestMatchers("/api/v1/room/**")
                                .hasRole("ADMIN")

                                .anyRequest()
                                .authenticated()
                               
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

//    // 2. AuthenticationProvider : this is enough for basic auth (line no 33 :  .httpBasic(Customizer.withDefaults());)
//    @Bean
//    public AuthenticationProvider provider(UserDetailsService userDetailsService) {
//        DaoAuthenticationProvider provider =
//                new DaoAuthenticationProvider(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(12);
    }
}
