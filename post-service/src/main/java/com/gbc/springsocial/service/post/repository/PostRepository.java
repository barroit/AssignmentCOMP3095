package com.gbc.springsocial.service.post.repository;

import com.gbc.springsocial.shared.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {
	Optional<Post> findBySlug(String slug);
	List<Post> findAllByUserId(String id);
	void deleteAllByUserId(String id);
}
