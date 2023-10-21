package com.gbc.springsocial.shared.exception;

public class AlreadyExistsException extends UniversalException {
	public AlreadyExistsException(String things) {
		super(String.format("The %s you provided already exists.", things));
	}
}
