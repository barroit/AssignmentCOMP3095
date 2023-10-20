package com.gbc.service.user.service;

import com.gbc.service.user.model.User;

import java.util.List;

public interface UserService {
    String create(String name);

    int update(User user);

	int delete(String id);

	List<User> get();

	User get(String id);
}
