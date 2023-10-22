package com.gbc.springsocial.service.friendship.service;

import com.gbc.springsocial.service.friendship.model.Relation;

import java.util.List;

public interface FriendshipService {
	List<Relation> status();
	Relation status(Relation relation);
	Relation request(Relation relation);
	Relation approval(Relation relation);
	Relation deny(Relation relation);
}
