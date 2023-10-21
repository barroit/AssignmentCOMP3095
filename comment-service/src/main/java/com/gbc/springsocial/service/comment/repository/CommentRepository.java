package com.gbc.springsocial.service.comment.repository;

import com.gbc.springsocial.shared.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
	List<Comment> findAllByPostId(String id);
}
