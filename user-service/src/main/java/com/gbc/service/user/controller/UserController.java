package com.gbc.service.user.controller;

import com.gbc.service.user.model.User;
import com.gbc.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public List<User> get() {
		return userService.get();
	}

	@GetMapping("/{id}")
	public User get(@PathVariable String id) {
		return userService.get(id);
	}

	@PostMapping("/{name}")
	public String create(@PathVariable String name) {
		return userService.create(name);
	}

	@PutMapping("/{id}")
	public String update(@PathVariable String id, @RequestBody User user) {
		return userService.update(user.setId(id)) + " row affected";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		return userService.delete(id) + " row affected";
	}
}
