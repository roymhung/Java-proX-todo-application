package vn.proX.todoapplication.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.proX.todoapplication.entity.ApiResponse;
import vn.proX.todoapplication.entity.User;
import vn.proX.todoapplication.service.impl.UserServiceImpl;

@RestController
public class UserController {

	private final UserServiceImpl userService;

	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
		User created = userService.createUser(user);

		var response =
				new ApiResponse<>(HttpStatus.CREATED, "User created successfully", created, null);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

		var response = new ApiResponse<>(HttpStatus.OK, "getAllUsers successfully",
				userService.getAllUsers(), null);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
		return userService.getUserById(id).map(user -> {
			var response = new ApiResponse<>(HttpStatus.OK, "getUserById", user, null);
			return ResponseEntity.ok(response);

		}).orElseGet(() -> {
			ApiResponse<User> errorResponse = new ApiResponse<>(HttpStatus.NOT_FOUND,
					"Không tìm thấy user với ID: " + id, null, "USER_NOT_FOUND");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		});
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id,
			@RequestBody User user) {
		User updated = userService.updateUser(id, user);
		var response = new ApiResponse<>(HttpStatus.OK, "User updated successfully", updated, null);
		return ResponseEntity.ok().body(response);
	}


	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		ApiResponse<User> response =
				new ApiResponse<>(HttpStatus.NO_CONTENT, "User deleted successfully", null, null);
		return ResponseEntity.ok().body(response);
	}
}
