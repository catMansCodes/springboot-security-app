package com.catmanscodes.securityapp.repository;

import com.catmanscodes.securityapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameAndPassword(String userName, String password);

    Optional<User> findByUserName(String userName);
}
