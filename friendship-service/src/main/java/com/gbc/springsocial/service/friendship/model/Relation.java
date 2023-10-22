package com.gbc.springsocial.service.friendship.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(value = "relation")
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Relation {
	@Id
	String id;

	@Field("from_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String fromId;
	@Transient
	String from;

	@Field("to_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String toId;
	@Transient
	String to;

	// pending, accepted, declined
	String status;
}
