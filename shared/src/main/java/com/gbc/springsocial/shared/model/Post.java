package com.gbc.springsocial.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(value = "post")
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
	@Id
	String id;
	@Indexed(unique = true)
	String slug;

	String title;
	String content;

	@Field("user_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String userId;
	@Transient
	String author;

	@Transient
	List<Comment> comments = new ArrayList<>();
}
