package com.gbc.springsocial.service.user.service;

import com.gbc.springsocial.shared.model.User;

import java.util.List;

public interface UserService {
	User create(User user);

	User update(User user);

	User delete(String id);

	List<User> get();

	User get(String type, String identifier);
}
