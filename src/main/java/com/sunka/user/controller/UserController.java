package com.sunka.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunka.user.model.UserModel;
import com.sunka.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/register")
    public UserModel register(@RequestBody UserModel userModel) {
        return userService.register(userModel);
    }

    @PostMapping("/validate")
    public boolean validateUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.validateUser(username, password);
    }
    
    @GetMapping("/exists")
    public ResponseEntity<Boolean> userExists(@RequestParam("username") String username) {
        boolean exists = userService.existsByUsernameIgnoreCase(username);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/find-by-email")
    public ResponseEntity<Map<String, Object>> findByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }
    
    @GetMapping("/find-by-username")
    public ResponseEntity<Map<String, Object>> findByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }


    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {
        return userService.updateUser(id, userModel);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
