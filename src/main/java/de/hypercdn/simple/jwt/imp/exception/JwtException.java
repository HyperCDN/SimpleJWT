package de.hypercdn.simple.jwt.imp.exception;

public class JwtException extends RuntimeException{

	public JwtException(String message){
		super(message);
	}

	public JwtException(String message, Throwable t){
		super(message, t);
	}

}
