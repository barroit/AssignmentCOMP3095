package com.gbc.service.user.service;

import com.gbc.service.user.model.User;
import com.gbc.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public String create(String name) {
		return userRepository.save(User.builder().name(name).build()).getId();
	}

	@Override
	public int update(User user) {
		User oldUser = userRepository.findById(user.getId()).orElse(null);
		if (oldUser == null) return 0;

		userRepository.save(user.setFriends(oldUser.getFriends()));
		return 1;
	}

	@Override
	public int delete(String id) {
		if (!userRepository.existsById(id)) return 0;
		userRepository.deleteById(id);
		return 1;
	}

	@Override
	public List<User> get() {
		return userRepository.findAll();
	}

	@Override
	public User get(String id) {
		return userRepository.findById(id).orElse(null);
	}
}
