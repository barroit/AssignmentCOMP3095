package com.gbc.springsocial.service.comment.service;

import com.gbc.springsocial.service.comment.repository.CommentRepository;
import com.gbc.springsocial.shared.Bridge;
import com.gbc.springsocial.shared.exception.MissingFieldException;
import com.gbc.springsocial.shared.exception.NotFoundException;
import com.gbc.springsocial.shared.exception.UniversalException;
import com.gbc.springsocial.shared.model.Comment;
import com.gbc.springsocial.shared.model.Post;
import com.gbc.springsocial.shared.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final RestTemplate postRestTemplate;
	private final RestTemplate userRestTemplate;
	private final CommentRepository commentRepository;

	/**
	 * Retrieves a list of comments associated with a given Post ID.
	 *
	 * @param id The ID of the Post for which comments are to be fetched.
	 * @return A list of comments corresponding to the provided Post ID.
	 */
	@Override
	public List<Comment> selectByPostId(String id) {
		return commentRepository.findAllByPostId(id).stream().peek(comment -> {
			try {
				User user = Bridge.getUserById(userRestTemplate, comment.getUserId());
				if (user != null) comment.setAuthor(user.getUsername());
			} catch (Exception ignore) { }
		}).toList();
	}

	/**
	 * Creates a new comment and associates it with a user and a post.
	 *
	 * @param comment The comment object containing information like content, userId, and postId.
	 * @return The created comment with the author's username set.
	 * @throws MissingFieldException If any required field in the comment object is missing.
	 * @throws NotFoundException     If the associated user or post is not found.
	 */
	@Override
	public Comment create(Comment comment) {
		if (comment.getContent() == null) throw new MissingFieldException("content");
		if (comment.getUserId() == null) throw new MissingFieldException("userId");
		if (comment.getPostId() == null) throw new MissingFieldException("postId");

		User user = Bridge.getUserById(userRestTemplate, comment.getUserId());
		if (user == null) throw new NotFoundException("user", comment.getUserId());

		Post post = Bridge.getPostById(postRestTemplate, comment.getPostId());
		if (post == null) throw new NotFoundException("post", comment.getPostId());

		return commentRepository.save(comment).setAuthor(user.getUsername());
	}

	/**
	 * Updates the content of an existing comment.
	 *
	 * @param comment The comment object containing the ID of the comment to be updated and the new content.
	 * @return The updated comment with the author's username set.
	 * @throws MissingFieldException If the ID or content in the comment object is missing.
	 * @throws NotFoundException     If the comment to be updated is not found.
	 * @throws UniversalException    If there's no content provided for the update.
	 */
	@Override
	public Comment update(Comment comment) {
		if (comment.getId() == null) throw new MissingFieldException("id");
		if (comment.getContent() == null) throw new UniversalException("Nothing to do");

		Comment oldComment = commentRepository.findById(comment.getId()).orElse(null);
		if (oldComment == null) throw new NotFoundException("comment", comment.getId());

		oldComment.setContent(comment.getContent());

		Comment updatedComment = commentRepository.save(oldComment);

		try {
			User user = Bridge.getUserById(userRestTemplate, updatedComment.getUserId());
			if (user != null) updatedComment.setAuthor(user.getUsername());
		} catch (Exception ignore) { }

		return updatedComment;
	}

	/**
	 * Deletes the comment with the specified ID.
	 *
	 * @param id The ID of the comment to be deleted.
	 * @return The deleted comment with the author's username set.
	 * @throws NotFoundException If the comment to be deleted is not found.
	 */
	@Override
	public Comment delete(String id) {
		Comment comment = commentRepository.findById(id).orElse(null);
		if (comment == null) throw new NotFoundException("comment", id);

		try {
			User user = Bridge.getUserById(userRestTemplate, comment.getUserId());
			if (user != null) comment.setAuthor(user.getUsername());
		} catch (Exception ignore) { }

		commentRepository.deleteById(id);
		return comment;
	}

	/**
	 * Deletes all comments associated with the specified Post ID.
	 *
	 * @param id The ID of the Post for which all related comments are to be deleted.
	 */
	@Override
	public void deleteByPost(String id) {
		commentRepository.findAllByPostId(id).forEach(comment -> commentRepository.deleteById(comment.getId()));
	}
}
