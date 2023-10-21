package com.gbc.springsocial.service.post.service;

import com.gbc.springsocial.shared.model.Post;

import java.util.List;

public interface PostService {
	List<Post> select();
	List<Post> select(String Username);
	Post select(String type, String identifier);
	Post create(Post post);
	Post update(Post post);
	Post delete(String id);
	void deleteByUser(String id);
}
