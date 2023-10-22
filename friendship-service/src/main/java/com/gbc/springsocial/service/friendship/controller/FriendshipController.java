package com.gbc.springsocial.service.friendship.controller;

import com.gbc.springsocial.service.friendship.model.Relation;
import com.gbc.springsocial.service.friendship.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendshipController {
	private final FriendshipService friendshipService;

	@GetMapping("/status")
	public Relation status(Relation relation) {
		return friendshipService.status(relation);
	}

	@GetMapping("/status/all")
	public List<Relation> status() {
		return friendshipService.status();
	}

	@PostMapping("/request")
	public Relation request(Relation relation) {
		return friendshipService.request(relation);
	}

	@PutMapping("/approval")
	public Relation approval(Relation relation) {
		return friendshipService.approval(relation);
	}

	@PutMapping("/deny")
	public Relation deny(Relation relation) {
		return friendshipService.deny(relation);
	}
}
