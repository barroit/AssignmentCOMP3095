package com.gbc.springsocial.shared.exception;

public class NotFoundException extends IllegalStateException {
	public NotFoundException(String target, String search) {
		super(String.format("Cannot find %s with %s", target, search));
	}
}
