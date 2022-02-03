package de.hypercdn.simplejwt.api;

import de.hypercdn.simplejwt.api.builder.JwtEntityBuilder;
import de.hypercdn.simplejwt.api.components.Claims;
import de.hypercdn.simplejwt.imp.exception.JwtException;

import java.time.Duration;
import java.util.Objects;

public interface JwtManager{

	/**
	 * Returns a new jwt builder linked to this manager
	 *
	 * @return jwt builder
	 */
	JwtEntityBuilder newJwt();

	/**
	 * Returns the internally stored issuer identification
	 *
	 * @return issuer id
	 */
	String getIssuerId();

	/**
	 * Verifies the provided jwt entity can be validated by this manager
	 *
	 * This will check both the issuer id and the jwt id
	 * @param jwtEntity to verify
	 * @return true if verified, false otherwise
	 */
	boolean verify(JwtEntity jwtEntity);

	/**
	 * Invalidates the provided jwt entity
	 *
	 * Will internally verify if the provided entity is valid and throw an exception if this is not the case
	 *
	 * @param jwtEntity to invalidate
	 * @return true if the action was successful
	 */
	default boolean invalidate(JwtEntity jwtEntity){
		Objects.requireNonNull(jwtEntity);
		if(!verify(jwtEntity)){
			throw new JwtException("Jwt is invalid");
		}
		var jwtId = jwtEntity.getClaims().getValueFor(Claims.Common.JWT_ID);
		return invalidate(jwtId.toString());
	}

	/**
	 * Invalidates the jwt matching the provided jwt id
	 *
	 * @param jti of the jwt
	 * @return true if the action was successful
	 */
	boolean invalidate(String jti);

	/**
	 * Invalidates all jwt managed by this manager
	 */
	void invalidateAll();

	/**
	 * Returns the stored global signing bytes
	 *
	 * @return bytes used for signing
	 */
	byte[] getGlobalSigningBytes();

	/**
	 * Returns the stored jwt dependent signing bytes
	 *
	 * @param jti of the jwt
	 * @return bytes used for signing
	 */
	byte[] getJwtSigningBytes(String jti);

	/**
	 * Returns a new set of signing bytes for the specified jwt id
	 *
	 * It is recommended to have it return a different sequence on every call even if the id is the same.
	 * However, some use cases might require a different behaviour
	 * @return new signing bytes
	 */
	byte[] receiveJwtSigningBytes(String jti);

	/**
	 * Returns a combined set of the global and jwt specific signing bytes which is used to sign and verify the token
	 *
	 * @param jti of the jwt
	 * @return combined set of the global and jwt specific signing bytes
	 */
	default byte[] getCombinedSigningBytes(String jti){
		Objects.requireNonNull(jti);
		var globalBytes = getGlobalSigningBytes();
		var keyBytes = getJwtSigningBytes(jti);
		if(keyBytes == null){
			return null;
		}
		var combinedBytes = new byte[globalBytes.length + keyBytes.length];
		System.arraycopy(globalBytes, 0, combinedBytes, 0, globalBytes.length);
		System.arraycopy(keyBytes, 0, combinedBytes, globalBytes.length, keyBytes.length);
		return combinedBytes;
	}

	/**
	 * Inserts a new set of signing bytes for the specified jwt id which will be kept for the defined duration
	 *
	 * @param jti of the jwt
	 * @param bytes jwt specific signing bytes, preferably used in combination with {@link #receiveJwtSigningBytes(String)}
	 * @param duration on how long to keep the signing bytes stored. It is recommended to make this duration similar or slightly longer than the set expiration of the jwt it links to
	 */
	void setSigningBytesFor(String jti, byte[] bytes, Duration duration);

}
