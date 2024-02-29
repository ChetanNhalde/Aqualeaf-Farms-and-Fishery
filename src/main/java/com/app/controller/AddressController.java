package com.app.controller;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AddressDTO;
import com.app.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employees/{userId}/address")
@Validated //needed for validations of path var n req params
@SecurityRequirement(name = "Aqualeaf")
public class AddressController {
	@Autowired
	private AddressService adrService;

	public AddressController() {
		System.out.println("in ctor of " + getClass());
	}

	// assign address to emp
	// http://host:port/employees/{userId}/address , method=POST
	@PostMapping
	@Operation(summary = "Assign Employee Address")
	public ResponseEntity<?> assignEmpAddress(@PathVariable @NotNull Long userId,
			@RequestBody @Valid AddressDTO address) {
		System.out.println("in assign adr " + userId + " " + address);
		return ResponseEntity.status(HttpStatus.CREATED).body(adrService.assignEmpAddress(userId, address));
	}

	// get emp address
	// http://host:port/employees/{userId}/address method=GET
	@GetMapping
	@Operation(summary = "Get employee address")
	public ResponseEntity<?> getEmpAddress(@PathVariable Long userId) {
		System.out.println("in get emp adr " + userId);
		// one to one with shared PK => emp id is same as adr id
		return ResponseEntity.ok(adrService.getAddressDetails(userId));
	}

	// update address COMPLETE
	// http://host:port/employees/{userId}/address , method=PUT
	@PutMapping
	@Operation(summary = "Complete updation of employee address")
	public ResponseEntity<?> updateEmpAddress(@PathVariable @NotNull Long userId,
			@RequestBody @Valid AddressDTO address) {
		System.out.println("in complete update adr " + userId + " " + address);
		return ResponseEntity.ok()
				.body(adrService.updateEmpAddress(userId, address));
	}

	// update address partial
	// http://host:port/employees/{userId}/address , method=PATCH
	@PatchMapping
	@Operation(summary = "Partial updation of employee address")
	public ResponseEntity<?> updateEmpAddressPartial(@PathVariable @NotNull Long userId,
			@RequestBody Map<String, Object> map) throws Exception{
		System.out.println("in partial update adr " + userId + " " + map);
		return ResponseEntity.ok()
				.body(adrService.patchEmpAddress(userId, map));
	}
}
