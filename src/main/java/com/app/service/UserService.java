package com.app.service;

import java.util.List;

import com.app.dto.ApiResponse;
import com.app.dto.SignUpDTO;
import com.app.dto.UserDTO;

public interface UserService {

	public List<UserDTO> getAllUsers();

	public UserDTO addNewUser(UserDTO userDTO);

	public UserDTO updateUser(Long id, UserDTO userDto);

	public ApiResponse deleteEmpDetails(Long id);

	public UserDTO getUser(Long id);
	
	SignUpDTO userRegistration(SignUpDTO reqDTO);

}
