package com.gbc.springsocial.service.post.controller;

import com.gbc.springsocial.shared.model.Post;
import com.gbc.springsocial.service.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@GetMapping("/select")
	public List<Post> select() {
		return postService.select();
	}

	@GetMapping("/select/user/{username}")
	public List<Post> select(@PathVariable String username) {
		return postService.select(username);
	}

	@GetMapping("/select/{type}/{identifier}")
	public Post select(@PathVariable String type, @PathVariable String identifier) {
		return postService.select(type, identifier);
	}

	@PostMapping("/create")
	public Post create(Post post) {
		return postService.create(post);
	}

	@PutMapping("/update")
	public Post update(Post post) {
		return postService.update(post);
	}

	@DeleteMapping("/delete/unique/{id}")
	public Post delete(@PathVariable String id) {
		return postService.delete(id);
	}

	@DeleteMapping("/delete/user/{id}")
	public void deleteByUser(@PathVariable String id) {
		postService.deleteByUser(id);
	}
}
