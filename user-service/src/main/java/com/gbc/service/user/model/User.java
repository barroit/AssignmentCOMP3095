package com.gbc.service.user.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@Document(value = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;

    String name;

    @DBRef
    List<User> friends;
}
