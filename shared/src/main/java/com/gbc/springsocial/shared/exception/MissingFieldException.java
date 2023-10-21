package com.gbc.springsocial.shared.exception;

public class MissingFieldException extends IllegalArgumentException {
	public MissingFieldException(String field) {
		super(String.format("Please provide the missing property: %s", field));
	}
}
