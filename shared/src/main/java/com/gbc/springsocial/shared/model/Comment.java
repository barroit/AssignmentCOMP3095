package com.gbc.springsocial.shared.model;

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
@Document(value = "comment")
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
	@Id
	String id;

	String content;

	@Field("user_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String userId;
	@Transient
	String author;

	@Field("post_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String postId;
}
