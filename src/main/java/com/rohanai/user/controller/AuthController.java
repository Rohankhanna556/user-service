package com.rohanai.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohanai.user.config.JwtTokenUtil;
import com.rohanai.user.dto.UserDTO;
import com.rohanai.user.entity.User;
import com.rohanai.user.model.UserLoginModel;
import com.rohanai.user.model.UserModel;
import com.rohanai.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserModel model) {
		UserDTO registeredUser = userService.registerUser(model);
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginModel authRequest) {
		User user = userService.findByEmail(authRequest.getEmail());
		if (user != null && userService.checkPassword(authRequest.getPassword(), user)) {
			String token = jwtTokenUtil.generateTokenLogin(user.getEmail(), user.getRole());
			return ResponseEntity.ok(token);
		} else {
			return ResponseEntity.status(401).body("Invalid username or password");
		}
	}
}