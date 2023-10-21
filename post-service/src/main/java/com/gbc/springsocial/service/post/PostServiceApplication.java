package com.gbc.springsocial.service.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

	@Value("${USER_SERVICE_HOST:localhost}")
	private String userServiceHost;

	@Value("${COMMENT_SERVICE_HOST:localhost}")
	private String commentServiceHost;

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}

	@Bean
	public RestTemplate userRestTemplate(RestTemplateBuilder builder) {
		return builder.rootUri(String.format("http://%s:3567/user", userServiceHost)).build();
	}

	@Bean
	public RestTemplate commentRestTemplate(RestTemplateBuilder builder) {
		return builder.rootUri(String.format("http://%s:3569/comment", commentServiceHost)).build();
	}

}
