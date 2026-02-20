package com.fileManager.file.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fileManager.file.Service.UserService;
import com.fileManager.file.models.User;
@RestController
public class UserController {
    @Autowired
        private UserService userService;
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> createUser(
        @RequestPart("user") User user, 
        @RequestPart("file") MultipartFile file
    ) {
        // Logique pour sauvegarder l'image et l'utilisateur
        User savedUser = userService.createUserWithPhoto(user, file);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
