package com.rohanai.user.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohanai.user.dto.UserDTO;
import com.rohanai.user.entity.User;
import com.rohanai.user.mapper.UserDTOMapper;
import com.rohanai.user.model.UserModel;
import com.rohanai.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDTOMapper mapper;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserDTO registerUser(UserModel model) {
		User user = new User();
		user.setFirstName(model.getFirstName());
		user.setLastName(model.getLastName());
		user.setEmail(model.getEmail());
		user.setMobile(model.getMobile());
		user.setRole(model.getRole());
		user.setFullName(model.getFirstName() + " " + model.getLastName());
		// Encrypt the password
		user.setPassword(passwordEncoder.encode(model.getPassword()));

		// Save the user to the database
		return mapper.convert(userRepository.save(user));
	}

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>()
        );
    }
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean checkPassword(String rawPassword, User user) {
		return passwordEncoder.matches(rawPassword, user.getPassword());
	}
}
