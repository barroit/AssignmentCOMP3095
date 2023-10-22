package com.gbc.springsocial.service.friendship.service;

import com.gbc.springsocial.service.friendship.model.Relation;
import com.gbc.springsocial.service.friendship.repository.FriendshipRepository;
import com.gbc.springsocial.shared.Bridge;
import com.gbc.springsocial.shared.exception.AlreadyExistsException;
import com.gbc.springsocial.shared.exception.MissingFieldException;
import com.gbc.springsocial.shared.exception.NotFoundException;
import com.gbc.springsocial.shared.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
	private final FriendshipRepository friendshipRepository;
	private final RestTemplate userRestTemplate;

	@Override
	public List<Relation> status() {
		return friendshipRepository.findAll().stream().peek(this::visualizeRelation).toList();
	}

	@Override
	public Relation status(Relation relation) {
		if (relation.getFromId() == null) throw new MissingFieldException("fromId");
		if (relation.getToId() == null) throw new MissingFieldException("toId");
		return friendshipRepository.findByFromIdAndToId(relation.getFromId(), relation.getToId())
				.map(this::visualizeRelation).orElse(null);
	}

	@Override
	public Relation request(Relation relation) {
		Relation relationship = status(relation);
		if (relationship != null) throw new AlreadyExistsException("request");

		User from = Bridge.getUserById(userRestTemplate, relation.getFromId());
		if (from == null) throw new NotFoundException("user", relation.getFromId());

		User to = Bridge.getUserById(userRestTemplate, relation.getToId());
		if (to == null) throw new NotFoundException("user", relation.getTo());

		relationship = new Relation().setFromId(from.getId()).setToId(to.getId()).setStatus("pending");
		return friendshipRepository.save(relationship).setFrom(from.getUsername()).setTo(to.getUsername());
	}

	@Override
	public Relation approval(Relation relation) {
		return changeRelation(relation, "accepted");
	}

	@Override
	public Relation deny(Relation relation) {
		return changeRelation(relation, "declined");
	}

	private Relation visualizeRelation(Relation relation) {
		try {
			User user = Bridge.getUserById(userRestTemplate, relation.getFromId());
			if (user != null) relation.setFrom(user.getUsername());
		} catch (Exception e) {
			relation.setFrom("unknown");
		}
		try {
			User user = Bridge.getUserById(userRestTemplate, relation.getToId());
			if (user != null) relation.setTo(user.getUsername());
		} catch (Exception e) {
			relation.setTo("unknown");
		}
		return relation;
	}

	private Relation changeRelation(Relation relation, String state) {
		if (relation.getFromId() == null) throw new MissingFieldException("fromId");
		if (relation.getToId() == null) throw new MissingFieldException("toId");

		Relation relationship = friendshipRepository.findByFromIdAndToId(relation.getFromId(), relation.getToId()).orElse(null);
		Relation inverseRelationship = friendshipRepository.findByToIdAndFromId(relation.getFromId(), relation.getToId()).orElse(null);

		if (relationship == null) throw new NotFoundException("request", "relation");
		if (inverseRelationship != null && !"pending".equals(inverseRelationship.getStatus()))
			throw new AlreadyExistsException("relation");

		Relation updatedRelation = friendshipRepository.save(relationship.setStatus(state));

		try {
			User from = Bridge.getUserById(userRestTemplate, relation.getFromId());
			if (from != null) updatedRelation.setFrom(from.getUsername());
		} catch (Exception ignore) { }

		try {
			User to = Bridge.getUserById(userRestTemplate, relation.getToId());
			if (to != null) updatedRelation.setTo(to.getUsername());
		} catch (Exception ignore) { }

		return updatedRelation;
	}
}
