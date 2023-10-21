package com.gbc.springsocial.service.comment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentServiceApplication.class, args);
	}

	@Value("${USER_SERVICE_HOST:localhost}")
	private String userServiceHost;

	@Value("${POST_SERVICE_HOST:localhost}")
	private String postServiceHOST;

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}

	@Bean
	public RestTemplate userRestTemplate(RestTemplateBuilder builder) {
		return builder.rootUri(String.format("http://%s:3567/user", userServiceHost)).build();
	}

	@Bean
	public RestTemplate postRestTemplate(RestTemplateBuilder builder) {
		return builder.rootUri(String.format("http://%s:3568/post", postServiceHOST)).build();
	}

}
