package com.cos.security1.repository;

import com.cos.security1.damain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository  extends JpaRepository<User,Integer> {

     User findByUsername(String username);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
