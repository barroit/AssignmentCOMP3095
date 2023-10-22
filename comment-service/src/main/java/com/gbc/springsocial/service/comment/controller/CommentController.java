package com.gbc.springsocial.service.comment.controller;

import com.gbc.springsocial.service.comment.service.CommentService;
import com.gbc.springsocial.shared.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@GetMapping("/select")
	List<Comment> select() {
		return commentService.select();
	}

	@GetMapping("/select/{id}")
	List<Comment> selectByPostId(@PathVariable String id) {
		return commentService.selectByPostId(id);
	}

	@PostMapping("/create")
	Comment create(Comment comment) {
		return commentService.create(comment);
	}

	@PutMapping("/update")
	Comment update(Comment comment) {
		return commentService.update(comment);
	}

	@DeleteMapping("/delete/unique/{id}")
	Comment delete(@PathVariable String id) {
		return commentService.delete(id);
	}

	@DeleteMapping("/delete/post/{id}")
	void deleteByPost(@PathVariable String id) {
		commentService.deleteByPost(id);
	}
}
