package com.gbc.springsocial.service.user.service;

import com.gbc.springsocial.service.user.model.User;
import com.gbc.springsocial.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public List<User> get() {
		return userRepository.findAll();
	}

	@Override
	public User get(String id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User create(User user) {
		if (user.getName() == null) {
			throw new IllegalArgumentException("Please provide the missing property: name");
		}
		if (user.getEmail() == null) {
			throw new IllegalArgumentException("Please provide the missing property: email");
		}
		if (userRepository.findByName(user.getName()).isPresent()) {
			throw new IllegalStateException("The user you provided already exists.");
		}

		return userRepository.save(user);
	}

	@Override
	public User update(User user) {
		if (user.getId() == null) {
			throw new IllegalArgumentException("Please provide the missing property: id");
		}

		if (user.getName() == null && user.getEmail() == null) {
			throw new IllegalStateException("Nothing to do");
		}

		User oldUser = userRepository.findById(user.getId()).orElse(null);
		if (oldUser == null) {
			throw new IllegalStateException(String.format("Cannot find user with id: %s", user.getId()));
		}

		if (user.getName() != null) {
			System.out.println(user.getName());
			oldUser.setName(user.getName());
		}

		if (user.getEmail() != null) {
			oldUser.setEmail(user.getEmail());
		}

		return userRepository.save(oldUser);
	}

	@Override
	public User delete(String id) {
		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new IllegalStateException(String.format("Cannot find user with id: %s", id));
		}

		userRepository.deleteById(id);
		return user;
	}
}
