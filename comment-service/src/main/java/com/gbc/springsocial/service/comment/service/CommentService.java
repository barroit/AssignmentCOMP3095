package com.gbc.springsocial.service.comment.service;

import com.gbc.springsocial.shared.model.Comment;

import java.util.List;

public interface CommentService {
	List<Comment> select();
	List<Comment> selectByPostId(String id);
	Comment create(Comment comment);
	Comment update(Comment comment);
	Comment delete(String id);
	void deleteByPost(String id);
}
