package com.gbc.springsocial.service.friendship.repository;

import com.gbc.springsocial.service.friendship.model.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FriendshipRepository extends MongoRepository<Relation, String> {
    Optional<Relation> findByFromIdAndToId(String from, String to);
    Optional<Relation> findByToIdAndFromId(String to, String from);
}