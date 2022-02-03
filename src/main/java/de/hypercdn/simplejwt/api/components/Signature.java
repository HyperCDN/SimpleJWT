package de.hypercdn.simplejwt.api.components;

/**
 * Represents the signature part of a jwt
 */
public interface Signature{

	/**
	 * Returns the signature of a jwt as string
	 *
	 * @return signature as string
	 */
	String asString();

}
