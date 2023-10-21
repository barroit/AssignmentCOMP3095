package com.gbc.springsocial.service.user.controller;

import com.gbc.springsocial.service.user.model.User;
import com.gbc.springsocial.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
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

	@PostMapping
	public Object create(User user) {
		return userService.create(user);
	}

	@PutMapping
	public Object update(User user) {
		return userService.update(user);
	}

	@DeleteMapping("/{id}")
	public Object delete(@PathVariable String id) {
		return userService.delete(id);
	}
}
