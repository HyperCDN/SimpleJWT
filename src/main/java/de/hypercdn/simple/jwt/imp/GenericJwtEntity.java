package de.hypercdn.simple.jwt.imp;

import de.hypercdn.simple.jwt.api.JwtEntity;
import de.hypercdn.simple.jwt.api.components.Claims;
import de.hypercdn.simple.jwt.api.components.Header;
import de.hypercdn.simple.jwt.api.components.Signature;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * Generic implementation of a jwt entity
 */
public class GenericJwtEntity implements JwtEntity{

	private final Header header;
	private final Claims claims;
	private final Signature signature;

	public GenericJwtEntity(Header header, Claims claims, Signature signature){
		Objects.requireNonNull(header);
		Objects.requireNonNull(claims);
		Objects.requireNonNull(signature);

		this.header = header;
		this.claims = claims;
		this.signature = signature;
	}

	@Override
	public Header getHeader(){
		return header;
	}

	@Override
	public Claims getClaims(){
		return claims;
	}

	@Override
	public Signature getSignature(){
		return signature;
	}

	@Override
	public String asJwtString(){
		var encodedHeader = Base64.getEncoder().withoutPadding().encodeToString(header.asString().getBytes(StandardCharsets.UTF_8));
		var encodedClaims = Base64.getEncoder().withoutPadding().encodeToString(claims.asString().getBytes(StandardCharsets.UTF_8));
		var storedSignature = signature.asString();
		return encodedHeader + "." + encodedClaims + "." + storedSignature;
	}

}
