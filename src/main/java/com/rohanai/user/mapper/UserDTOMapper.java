package com.rohanai.user.mapper;

import org.springframework.stereotype.Component;

import com.rohanai.user.dto.UserDTO;
import com.rohanai.user.entity.User;

@Component
public class UserDTOMapper {

	public UserDTO convert(User source) {
		UserDTO destination = new UserDTO();
		destination.setFirstName(source.getFirstName());
		destination.setLastname(source.getLastName());
		destination.setEmail(source.getEmail());
		destination.setMobile(source.getMobile());
		destination.setRole(source.getRole());
		return destination;
	}
}
