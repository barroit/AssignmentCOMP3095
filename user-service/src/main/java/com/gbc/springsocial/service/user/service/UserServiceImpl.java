package com.gbc.springsocial.service.user.service;

import com.gbc.springsocial.shared.Bridge;
import com.gbc.springsocial.shared.exception.NotFoundException;
import com.gbc.springsocial.shared.exception.MissingFieldException;
import com.gbc.springsocial.service.user.repository.UserRepository;
import com.gbc.springsocial.shared.exception.UniversalException;
import com.gbc.springsocial.shared.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RestTemplate postRestTemplate;

	/**
	 * Retrieves all users from the database.
	 *
	 * @return A list of all stored users.
	 */
	@Override
	public List<User> get() {
		return userRepository.findAll();
	}

	/**
	 * Retrieves a user based on the provided type and identifier.
	 *
	 * @param type       The type of identifier used (either "name" for username or any other value for user ID).
	 * @param identifier The username or user ID based on the provided type.
	 * @return The User object corresponding to the provided identifier or null if not found.
	 */
	@Override
	public User get(String type, String identifier) {
		return ("name".equals(type) ? userRepository.findByUsername(identifier) : userRepository.findById(identifier)).orElse(null);
	}

	/**
	 * Creates a new user entry in the database.
	 *
	 * @param user The User object containing details of the user to be created.
	 * @return The User object that was saved to the database.
	 * @throws MissingFieldException If either the username or email of the provided user is null.
	 * @throws UniversalException    If a user with the provided username already exists.
	 */
	@Override
	public User create(User user) {
		if (user.getUsername() == null) throw new MissingFieldException("username");
		if (user.getEmail() == null) throw new MissingFieldException("email");
		if (userRepository.findByUsername(user.getUsername()).isPresent())
			throw new UniversalException("The user you provided already exists.");

		return userRepository.save(user);
	}

	/**
	 * Updates an existing user's details in the database.
	 *
	 * @param user The User object containing updated details of the user.
	 * @return The updated User object that was saved to the database.
	 * @throws MissingFieldException If the ID of the provided user is null.
	 * @throws UniversalException    If both the username and email of the provided user are null.
	 * @throws NotFoundException     If no user with the provided ID exists.
	 */
	@Override
	public User update(User user) {
		if (user.getId() == null) throw new MissingFieldException("id");
		if (user.getUsername() == null && user.getEmail() == null) throw new UniversalException("Nothing to do");

		User oldUser = userRepository.findById(user.getId()).orElse(null);
		if (oldUser == null) throw new NotFoundException("user", user.getId());

		if (user.getUsername() != null) oldUser.setUsername(user.getUsername());

		if (user.getEmail() != null) oldUser.setEmail(user.getEmail());

		return userRepository.save(oldUser);
	}

	@Override
	public User delete(String id) {
		User user = userRepository.findById(id).orElse(null);
		if (user == null) throw new NotFoundException("user", id);

		Bridge.deletePostByUser(postRestTemplate, id);

		userRepository.deleteById(id);
		return user;
	}
}
