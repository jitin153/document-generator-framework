package com.jitin.documentgeneratorframework.exception;

public class DocumentGeneratorException extends RuntimeException {

	public DocumentGeneratorException(String message, Throwable cause) {
		super(message);
	}

	public DocumentGeneratorException(String message) {
		super(message);
	}
}
