package com.web.investment.controller;

import com.web.investment.model.User;
import com.web.investment.service.AuthService;
// import com.web.investment.exception.EmailAlreadyExistsException;
// import com.web.investment.service.WalletService;
// import com.web.investment.exception.InvalidCredentialsException;
import com.web.investment.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // User Registration API
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Call the registerUser method from AuthService
        User newUser = authService.registerUser(user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getPassword());
        return ResponseEntity.ok(newUser);  // Return the registered user
    }

    // User Login API
@PostMapping("/login")
public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User loginRequest) {
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    User loggedInUser = authService.loginUser(email, password);

    // Creating a response map to include the user ID
    Map<String, Object> response = new HashMap<>();
    response.put("id", loggedInUser.getId()); // Add the user ID
    response.put("message", "Login successful"); // Add any other message if required

    return ResponseEntity.ok(response);
}


    // User Update Profile API
    @PutMapping("/update/{userId}")
public ResponseEntity<User> updateUserProfile(@PathVariable Long userId, @RequestBody User user) {
    
        User updatedUser = authService.updateUserProfile(userId, user.getFullName(), user.getEmail(), user.getPhoneNumber());
        return ResponseEntity.ok(updatedUser);

    }

 
    @GetMapping("/user/{userId}")
public ResponseEntity<User> getUserDetails(@PathVariable Long userId) {
    try {
        // Call the service to get the user by userId
        User user = authService.getUserById(userId);
        return ResponseEntity.ok(user);
    } catch (ResourceNotFoundException e) {
        // If user is not found, return a 404 status
        return ResponseEntity.status(404).body(null);
    }
}

    

    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logoutUser(@PathVariable Long userId) {
        try {
            // Call the service to logout the user
            authService.logoutUser(userId);
            return ResponseEntity.ok("User logged out successfully.");
        } catch (ResourceNotFoundException e) {
            // If user is not found, return a 404 response
            return ResponseEntity.status(404).body("User with ID " + userId + " not found.");
        }
    }
    
}
