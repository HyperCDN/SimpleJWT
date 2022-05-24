package de.hypercdn.simple.jwt.imp;

import de.hypercdn.simple.jwt.api.JwtEntity;
import de.hypercdn.simple.jwt.api.JwtManager;
import de.hypercdn.simple.jwt.api.builder.JwtEntityBuilder;
import de.hypercdn.simple.jwt.imp.exception.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Generic implementation of a jwt entity builder
 */
public class GenericJwtEntityBuilder implements JwtEntityBuilder{

	private final JwtManager jwtEntityManager;

	private String subject = null;
	private String audience = null;
	private Date expiration = null;
	private Date notBefore = null;

	private final JSONObject claims = new JSONObject();
	private final JSONObject header = new JSONObject();

	public GenericJwtEntityBuilder(JwtManager jwtEntityManager){
		this.jwtEntityManager = jwtEntityManager;
	}

	@Override
	public JwtEntityBuilder setSubject(String sub){
		this.subject = sub;
		return this;
	}

	@Override
	public JwtEntityBuilder setAudience(String aud){
		this.audience = aud;
		return this;
	}

	@Override
	public JwtEntityBuilder setExpiration(Date exp){
		this.expiration = exp;
		return this;
	}

	@Override
	public JwtEntityBuilder setNotBefore(Date nbf){
		this.notBefore = nbf;
		return this;
	}

	@Override
	public JwtEntityBuilder addHeader(JSONObject jsonObject){
		Objects.requireNonNull(jsonObject);
		for(var key : jsonObject.keySet()){
			header.put(key, jsonObject.get(key));
		}
		return this;
	}

	@Override
	public JwtEntityBuilder addClaims(JSONObject jsonObject){
		Objects.requireNonNull(jsonObject);
		for(var key : jsonObject.keySet()){
			claims.put(key, jsonObject.get(key));
		}
		return this;
	}

	@Override
	public synchronized JwtEntity build(){

		var keyId = UUID.randomUUID().toString();

		var jwtBuilder = Jwts.builder()
			.setIssuer(jwtEntityManager.getIssuerId())
			.setIssuedAt(Date.from(Instant.now()))
			.setId(keyId)
			.setExpiration(expiration)
			.setNotBefore(notBefore)
			.setAudience(audience)
			.setSubject(subject)
			.addClaims(claims.toMap())
			.setHeaderParams(header.toMap());

		if(expiration == null){
			throw new JwtException("Expiration required");
		}

		jwtEntityManager.setSigningBytesFor(keyId, jwtEntityManager.getJwtSigningBytes(keyId), Duration.between(Instant.now(), expiration.toInstant()));

		var jwtString = jwtBuilder.signWith(Keys.hmacShaKeyFor(jwtEntityManager.getCombinedSigningBytes(keyId))).compact();

		return JwtEntity.fromString(jwtString);
	}

}
