package de.hypercdn.simplejwt.imp;

import de.hypercdn.simplejwt.api.JwtManager;
import de.hypercdn.simplejwt.api.builder.JwtManagerBuilder;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.function.Function;

/**
 * Generic implementation of a jwt manager
 * <p>
 * If no issuer id is set, a random uuid will be used instead
 * If no global signing bytes have been provider, 1024 random bytes will be used instead
 * If no jwt signing bytes provider has been set, a generic one providing 1024 random bytes will be used instead
 * <p>
 * If no global signing bytes have been provided,
 */
public class GenericJwtManagerBuilder implements JwtManagerBuilder{

	private String issuerId;
	private byte[] globalSigningBytes;
	private Function<String, byte[]> jwtSigningBytesProvider;

	@Override
	public JwtManagerBuilder setIssuerId(String issuerId){
		this.issuerId = issuerId;
		return this;
	}

	@Override
	public JwtManagerBuilder setGlobalSigningBytes(byte[] bytes){
		this.globalSigningBytes = bytes;
		return this;
	}

	@Override
	public JwtManagerBuilder setJwtSigningBytesProvider(Function<String, byte[]> jwtSigningBytesProvider){
		this.jwtSigningBytesProvider = jwtSigningBytesProvider;
		return this;
	}

	@Override
	public JwtManager build(){
		var secure = new SecureRandom();
		if(issuerId == null){
			issuerId = UUID.randomUUID().toString();
		}
		if(globalSigningBytes == null){
			globalSigningBytes = new byte[1024];
			secure.nextBytes(globalSigningBytes);
		}
		if(jwtSigningBytesProvider == null){
			jwtSigningBytesProvider = (unused) -> {
				var bytes = new byte[1024];
				secure.nextBytes(bytes);
				return bytes;
			};
		}
		return new GenericJwtManager(issuerId, globalSigningBytes, jwtSigningBytesProvider);
	}

}
