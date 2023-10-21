package com.gbc.springsocial.shared.exception;

public class ServiceUnavailableException extends UniversalException {
	public ServiceUnavailableException(String service) {
		super(String.format("The %s service is currently unavailable", service));
	}
}
