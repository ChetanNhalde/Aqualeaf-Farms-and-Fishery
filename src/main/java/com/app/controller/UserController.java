package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserDTO;
import com.app.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Aqualeaf")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin/users")
	public ResponseEntity<?> getUsers(){
		List<UserDTO> users = userService.getAllUsers();
		
		if(users.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(users);
	}
	

	@PutMapping("/public/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
		return ResponseEntity.ok(userService.updateUser(id, userDto));
	}
	
	
	@DeleteMapping("/admin/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		return ResponseEntity.ok(userService.deleteEmpDetails(id));
	}
	
	
	@GetMapping("/public/users/{id}") 
	public ResponseEntity<?> getUser(@PathVariable Long id){
		
		return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUser(id));
		
	}
	
}
