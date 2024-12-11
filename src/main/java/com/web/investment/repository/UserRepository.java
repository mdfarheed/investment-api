package com.web.investment.repository;

import com.web.investment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (useful for login functionality)
    Optional<User> findByEmail(String email);
    
    // Check if a user exists by email
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long userId);
}

