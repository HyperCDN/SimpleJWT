package de.hypercdn.simple.jwt.api;

import de.hypercdn.simple.jwt.api.components.Claims;
import de.hypercdn.simple.jwt.api.components.Header;
import de.hypercdn.simple.jwt.api.components.Signature;
import de.hypercdn.simple.jwt.imp.GenericJwtEntity;
import de.hypercdn.simple.jwt.imp.components.GenericClaims;
import de.hypercdn.simple.jwt.imp.components.GenericHeader;
import de.hypercdn.simple.jwt.imp.components.GenericSignature;
import de.hypercdn.simple.jwt.imp.exception.JwtException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;

public interface JwtEntity{

	Pattern PATTERN = Pattern.compile("^([a-zA-Z0-9_=]+)\\.([a-zA-Z0-9_=]+)\\.([a-zA-Z0-9_\\-\\+\\/=]*)$");

	/**
	 * Parses a jwt entity from a provided string
	 *
	 * @param jwt as string
	 *
	 * @return parsed jwt entity
	 */
	static JwtEntity fromString(String jwt){
		Objects.requireNonNull(jwt);

		if(!PATTERN.matcher(jwt).matches()){
			throw new JwtException("Not a valid jwt string");
		}
		var headerEncoded = jwt.substring(0, jwt.indexOf("."));
		var claimsEncoded = jwt.substring(jwt.indexOf(".") + 1, jwt.lastIndexOf("."));
		var signatureString = jwt.substring(jwt.lastIndexOf(".") + 1);

		var headerDecoded = new String(Base64.getDecoder().decode(headerEncoded));
		var claimsDecoded = new String(Base64.getDecoder().decode(claimsEncoded));

		var header = new GenericHeader(new JSONObject(headerDecoded));
		var claims = new GenericClaims(new JSONObject(claimsDecoded));
		var signature = new GenericSignature(signatureString);

		return new GenericJwtEntity(header, claims, signature);
	}

	/**
	 * Returns the header contents of this jwt
	 *
	 * @return header contents
	 */
	Header getHeader();

	/**
	 * Returns the claims of this jwt
	 *
	 * @return claim contents
	 */
	Claims getClaims();

	/**
	 * Returns the signature of this jwt
	 *
	 * @return signature
	 */
	Signature getSignature();

	/**
	 * Returns this jwt entity as an url safe string
	 *
	 * @return jwt string
	 */
	String asJwtString();

}
