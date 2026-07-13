package com.sunka.user.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunka.user.entity.User;
import com.sunka.user.model.UserModel;
import com.sunka.user.repo.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository repo, PasswordEncoder encoder,
    		ObjectMapper objectMapper) {
        this.userRepository = repo;
        this.passwordEncoder = encoder;
        this.objectMapper = objectMapper;
    }

    // Register new user
    public UserModel register(UserModel userModel) {
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(userModel.getRole() != null ? userModel.getRole() : "USER"); // default role

        User saved = userRepository.save(user);
        return toModel(saved);
    }

    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && passwordEncoder.matches(password, user.getPassword());    
    }
    
    // Get all users
    public List<UserModel> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    // Get user by ID
    public UserModel getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toModel)
                .orElse(null);
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Update user (including role)
    public UserModel updateUser(Long id, UserModel userModel) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userModel.getUsername());
            user.setEmail(userModel.getEmail());
            if (userModel.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userModel.getPassword()));
            }
            if (userModel.getRole() != null) {
                user.setRole(userModel.getRole());
            }
            User updated = userRepository.save(user);
            return toModel(updated);
        }).orElse(null);
    }

    // Helper method to convert Entity -> Model
    private UserModel toModel(User user) {
        return UserModel.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

	public Map<String, Object> findByEmail(String email) {
		 return userRepository.findByEmailIgnoreCase(email)
                .map(user -> objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {}))
                .orElse(null);
    }
	
	public Map<String, Object> findByUsername(String username) {
		 return userRepository.findByUsername(username)
               .map(user -> objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {}))
               .orElse(null);
   }

	public boolean existsByUsernameIgnoreCase(String username) {
		return userRepository.existsByUsernameIgnoreCase(username);
	}
}
