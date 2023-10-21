package com.gbc.springsocial.service.post.service;

import com.gbc.springsocial.shared.Bridge;
import com.gbc.springsocial.shared.exception.*;
import com.gbc.springsocial.shared.model.Comment;
import com.gbc.springsocial.shared.model.Post;
import com.gbc.springsocial.service.post.repository.PostRepository;
import com.gbc.springsocial.shared.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	private final RestTemplate userRestTemplate;
	private final RestTemplate commentRestTemplate;

	/**
	 * Retrieves a list of all available Posts.
	 *
	 * @return A list of Post objects with associated author and comments.
	 */
	@Override
	public List<Post> select() {
		return postRepository.findAll().stream().peek(post -> {
			try {
				User user = Bridge.getUserById(userRestTemplate, post.getUserId());
				if (user != null) post.setAuthor(user.getUsername());
			} catch (Exception ignore) { }
			try {
				List<Comment> comments = Bridge.getCommentsByPostId(commentRestTemplate, post.getId());
				if (comments != null) post.setComments(comments);
			} catch (Exception ignore) { }
		}).toList();
	}

	/**
	 * Retrieves a list of Posts associated with a specific username.
	 *
	 * @param username The username for which associated Posts are to be retrieved.
	 * @return A list of Post objects associated with the given username, along with their authors and comments.
	 */
	@Override
	public List<Post> select(String username) {
		User user = Bridge.getUserByName(userRestTemplate, username);
		if (user == null) throw new NotFoundException("user", username);
		return postRepository.findAllByUserId(user.getId()).stream().peek(post -> {
			post.setAuthor(user.getUsername());
			try {
				List<Comment> comments = Bridge.getCommentsByPostId(commentRestTemplate, post.getId());
				if (comments != null) post.setComments(comments);
			} catch (Exception ignore) { }
		}).toList();
	}

	/**
	 * Retrieves a Post based on the provided type and identifier.
	 *
	 * @param type       The category or method of search (e.g., "id", "slug").
	 * @param identifier The specific value used for searching the Post.
	 * @return A Post object that matches the given criteria or null if no match is found.
	 */
	@Override
	public Post select(String type, String identifier) {
		Post post = ("slug".equals(type) ? postRepository.findBySlug(identifier) : postRepository.findById(identifier)).orElse(null);
		if (post == null) throw new NotFoundException("post", identifier);

		try {
			User user = Bridge.getUserById(userRestTemplate, post.getUserId());
			post.setAuthor(user.getUsername());
		} catch (Exception ignore) { }

		try {
			List<Comment> comments = Bridge.getCommentsByPostId(commentRestTemplate, post.getId());
			if (comments != null) post.setComments(comments);
		} catch (Exception ignore) { }

		return post;
	}

	/**
	 * Creates a new Post after validating necessary fields and ensuring uniqueness of slug.
	 *
	 * @param post The Post object to be created.
	 * @return The saved Post object with updated user ID.
	 * @throws MissingFieldException  if any required field is missing.
	 * @throws AlreadyExistsException if the provided slug already exists.
	 * @throws NotFoundException      if the provided author's name doesn't correspond to any user.
	 */
	@Override
	public Post create(Post post) {
		if (post.getTitle() == null) throw new MissingFieldException("title");
		if (post.getSlug() == null) throw new MissingFieldException("slug");
		if (post.getContent() == null) throw new MissingFieldException("content");
		if (post.getAuthor() == null) throw new MissingFieldException("author");

		if (postRepository.findBySlug(post.getSlug()).isPresent()) throw new AlreadyExistsException("post slug");

		User user = Bridge.getUserByName(userRestTemplate, post.getAuthor());
		if (user == null) throw new NotFoundException("user", post.getAuthor());

		post.setUserId(user.getId());

		return postRepository.save(post);
	}

	/**
	 * Updates an existing Post using provided fields, maintaining uniqueness of slug.
	 *
	 * @param post The Post object with updated fields.
	 * @return The updated Post object.
	 * @throws MissingFieldException  if the Post's ID is missing.
	 * @throws NotFoundException      if the provided ID doesn't correspond to any post.
	 * @throws AlreadyExistsException if trying to set a slug that already exists.
	 */
	@Override
	public Post update(Post post) {
		if (post.getId() == null) throw new MissingFieldException("id");

		Post oldPost = postRepository.findById(post.getId()).orElse(null);
		if (oldPost == null) throw new NotFoundException("post", post.getId());

		if (post.getTitle() != null) oldPost.setTitle(post.getTitle());

		if (post.getContent() != null) oldPost.setContent(post.getContent());

		if (post.getSlug() != null) {
			if (!post.getSlug().equals(oldPost.getSlug()) && postRepository.findBySlug(post.getSlug()).isPresent())
				throw new AlreadyExistsException("post slug");
			oldPost.setSlug(post.getSlug());
		}

		Post updatedPost = postRepository.save(oldPost);

		try {
			User user = Bridge.getUserById(userRestTemplate, updatedPost.getUserId());
			if (user != null) updatedPost.setAuthor(user.getUsername());
		} catch (Exception ignore) { }

		return updatedPost;
	}

	/**
	 * Deletes the specified Post and its associated comments.
	 *
	 * @param id The ID of the Post to be deleted.
	 * @return The deleted Post object.
	 * @throws NotFoundException if the provided ID doesn't correspond to any post.
	 */
	@Override
	public Post delete(String id) {
		Post post = postRepository.findById(id).orElse(null);
		if (post == null) throw new NotFoundException("post", id);

		Bridge.deleteComments(commentRestTemplate, post.getId());

		postRepository.deleteById(id);

		try {
			User user = Bridge.getUserById(userRestTemplate, post.getUserId());
			if (user != null) post.setAuthor(user.getUsername());
		} catch (Exception ignore) { }

		return post;
	}

	/**
	 * Deletes all posts and their associated comments for the specified User ID.
	 *
	 * @param id The ID of the User for whom all related posts and comments are to be deleted.
	 */
	@Override
	public void deleteByUser(String id) {
		postRepository.findAllByUserId(id).forEach(post -> Bridge.deleteComments(commentRestTemplate, post.getId()));
		postRepository.deleteAllByUserId(id);
	}
}
