package de.hypercdn.simple.jwt.api.helper;

public interface CommonKey{

	/**
	 * Returns the actual key of this container
	 *
	 * @return key
	 */
	String getKey();

	/**
	 * Returns the expected type for the given common key
	 *
	 * @return expected type
	 */
	Class<?> getType();

}
