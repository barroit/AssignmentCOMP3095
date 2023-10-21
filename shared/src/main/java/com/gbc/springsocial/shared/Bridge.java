package com.gbc.springsocial.shared;

import com.gbc.springsocial.shared.exception.ServiceUnavailableException;
import com.gbc.springsocial.shared.exception.UniversalException;
import com.gbc.springsocial.shared.model.Comment;
import com.gbc.springsocial.shared.model.Post;
import com.gbc.springsocial.shared.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Bridge {
	public static User getUserById(RestTemplate restTemplate, String id) {
		try {
			return restTemplate.getForObject(String.format("/select/id/%s", id), User.class);
		} catch (ResourceAccessException e) {
			throw new ServiceUnavailableException("user");
		} catch (Exception e) {
			throw new UniversalException("unexpected error calling user service");
		}
	}

	public static User getUserByName(RestTemplate restTemplate, String name) {
		try {
			return restTemplate.getForObject(String.format("/select/name/%s", name), User.class);
		} catch (ResourceAccessException e) {
			throw new ServiceUnavailableException("user");
		} catch (Exception e) {
			throw new UniversalException("unexpected error calling user service");
		}
	}

	public static Post getPostById(RestTemplate restTemplate, String id) {
		try {
			return restTemplate.getForObject(String.format("/select/id/%s", id), Post.class);
		} catch (ResourceAccessException e) {
			throw new ServiceUnavailableException("post");
		} catch (Exception e) {
			throw new UniversalException("unexpected error calling post service");
		}
	}

	public static void deletePostByUser(RestTemplate restTemplate, String id) {
		try {
			restTemplate.delete(String.format("/delete/user/%s", id));
		} catch (ResourceAccessException e) {
			throw new ServiceUnavailableException("post");
		} catch (Exception e) {
			throw new UniversalException("unexpected error calling post service");
		}
	}

	public static List<Comment> getCommentsByPostId(RestTemplate restTemplate, String id) {
		try {
			return restTemplate.exchange(String.format("/select/%s", id), HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() { })
					.getBody();
		} catch (ResourceAccessException e) {
			throw new ServiceUnavailableException("comment");
		} catch (Exception e) {
			throw new UniversalException("unexpected error calling comment service");
		}
	}

	public static void deleteComments(RestTemplate restTemplate, String id) {
		try {
			restTemplate.delete(String.format("/delete/post/%s", id));
		} catch (ResourceAccessException e) {
			throw new ServiceUnavailableException("comment");
		} catch (Exception e) {
			throw new UniversalException("unexpected error calling comment service");
		}
	}
}
