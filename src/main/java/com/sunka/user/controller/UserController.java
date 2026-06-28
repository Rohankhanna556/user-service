package com.sunka.user.controller;

import org.springframework.web.bind.annotation.*;
import com.sunka.user.model.UserModel;
import com.sunka.user.service.UserService;

import java.util.List;

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
    public boolean validateUser(@RequestParam String username, @RequestParam String password) {
        return userService.validateUser(username, password);
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
