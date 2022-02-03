package de.hypercdn.simplejwt.imp.components;

import de.hypercdn.simplejwt.api.components.Signature;

import java.util.Objects;

public class GenericSignature implements Signature{

	private final String signature;

	public GenericSignature(String signature){
		Objects.requireNonNull(signature);
		this.signature = signature;
	}

	@Override
	public String asString(){
		return signature;
	}

	@Override
	public String toString(){
		return asString();
	}

}
