package de.hypercdn.simplejwt.api.builder;

import de.hypercdn.simplejwt.api.JwtManager;

import java.util.function.Function;

public interface JwtManagerBuilder extends Builder<JwtManager>{

	/**
	 * Sets the issuer id the manager should be using
	 *
	 * @param issuerId to use
	 * @return current instance
	 */
	JwtManagerBuilder setIssuerId(String issuerId);

	/**
	 * Sets the global part of the signing bytes
	 *
	 * @param bytes global
	 * @return current instance
	 */
	JwtManagerBuilder setGlobalSigningBytes(byte[] bytes);

	/**
	 * Specifies provider to receive the jwt specific signing bytes
	 *
	 * @param signingBytesProvider to set
	 * @return current instance
	 */
	JwtManagerBuilder setJwtSigningBytesProvider(Function<String, byte[]> signingBytesProvider);

}
