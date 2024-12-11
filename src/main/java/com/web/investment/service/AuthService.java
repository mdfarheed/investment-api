package com.web.investment.service;

import com.web.investment.model.User;
import com.web.investment.repository.UserRepository;
import com.web.investment.exception.ResourceNotFoundException;
// import com.web.investment.exception.EmailAlreadyExistsException;
import com.web.investment.exception.InvalidCredentialsException;

// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String fullName, String email, String phoneNumber, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exits");
        }
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password); // Plain text password
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;
    }

    public User updateUserProfile(Long userId, String fullName, String email, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    
        // Check if the email is already in use by another user (not the current one)
        if (userRepository.existsByEmailAndIdNot(email, userId)) {
            throw new IllegalArgumentException("Email already exists");
        }
    
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
    
        return userRepository.save(user);
    }
    

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    


    public void logoutUser(Long userId) {
        // Check if the user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }
    
        // Optionally handle token invalidation or other logic (if needed for JWT)
        // No need to log anything in this method
    }
    
}
