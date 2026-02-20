
package com.fileManager.file.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileManager.file.Repository.UserRepository;
import com.fileManager.file.models.User;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/api/users")
public class UserControllers {
    
    @Autowired
    private UserRepository  userRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user,@Nullable MultipartFile profilePicture) {
        // Logic to save the user and handle the profile picture
        
        return ResponseEntity.ok(userRepository.save(user));
    }
}
