package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.UserDao;
import com.app.dto.ApiResponse;
import com.app.dto.SignUpDTO;
import com.app.dto.UserDTO;
import com.app.entities.Cart;
import com.app.entities.User;
import com.app.entities.UserType;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userDao.findAll();
		return users.
				stream()
				.map(user -> mapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO addNewUser(UserDTO userDTO) {
		User user = mapper.map(userDTO, User.class);
		user.setType(UserType.USER);
		
		Cart cart = new Cart();
		user.setCart(cart);
		User savedUser = userDao.save(user);
		
		cart.setUser(savedUser);
		
		return mapper.map(savedUser, UserDTO.class);
		
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDto) {
		User user = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid User Id!!!"));
		mapper.map(userDto, user);
		userDto.setId(id);
		return userDto;
	}

	@Override
	public ApiResponse deleteEmpDetails(Long id) {
		User user = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid User Id!!!"));
		userDao.delete(user);
		return new ApiResponse("User Details of user with ID " + user.getId() + " deleted....");
	}

	@Override
	public UserDTO getUser(Long id) {
		User user = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid User Id!!!"));
		return mapper.map(user, UserDTO.class);
	}

	@Override
	public SignUpDTO userRegistration(SignUpDTO reqDTO) {
		User user=mapper.map(reqDTO, User.class);
		user.setType(UserType.USER);
		Cart cart = new Cart();
		user.setCart(cart);
		user.setPassword(encoder.encode(user.getPassword()));//pwd : encrypted using SHA
		User savedUser = userDao.save(user);
		cart.setUser(savedUser);
		return mapper.map(savedUser, SignUpDTO.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
