package de.hypercdn.simplejwt.api.builder;

import de.hypercdn.simplejwt.api.JwtEntity;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public interface JwtEntityBuilder extends Builder<JwtEntity>{

	/**
	 * Sets the subject field of the jwt
	 *
	 * @param sub ject
	 * @return current instance
	 */
	JwtEntityBuilder setSubject(String sub);

	/**
	 * Sets the audience field of the jwt
	 *
	 * @param aud ience
	 * @return current instance
	 */
	JwtEntityBuilder setAudience(String aud);

	/**
	 * Sets the expiration date of the jwt
	 *
	 * @param duration as offset from when the set call has been made
	 * @return current instance
	 */
	default JwtEntityBuilder setExpiration(Duration duration){
		Objects.requireNonNull(duration);
		var date = Date.from(Instant.now().plus(duration));
		return setExpiration(date);
	}

	/**
	 * Sets the expiration of the jwt
	 *
	 * @param localDateTime as timestamp
	 * @return current instance
	 */
	default JwtEntityBuilder setExpiration(LocalDateTime localDateTime){
		Objects.requireNonNull(localDateTime);
		var date = Date.from(Instant.from(localDateTime));
		return setExpiration(date);
	}

	/**
	 * Sets the expiration of the jwt
	 *
	 * @param exp iration
	 * @return current instance
	 */
	JwtEntityBuilder setExpiration(Date exp);

	/**
	 * Sets the "not before" field of the jwt
	 *
	 * @param duration as offset from when the set call has been made
	 * @return current instance
	 */
	default JwtEntityBuilder setNotBefore(Duration duration){
		Objects.requireNonNull(duration);
		var date = Date.from(Instant.now().plus(duration));
		return setNotBefore(date);
	}

	/**
	 * Sets the "not before" field of the jwt
	 *
	 * @param localDateTime as timestamp
	 * @return current instance
	 */
	default JwtEntityBuilder setNotBefore(LocalDateTime localDateTime){
		Objects.requireNonNull(localDateTime);
		var date = Date.from(Instant.from(localDateTime));
		return setNotBefore(date);
	}

	/**
	 * Sets the "not before" field of the jwt
	 *
	 * @param nbf not before
	 * @return current instance
	 */
	JwtEntityBuilder setNotBefore(Date nbf);

	/**
	 * Adds the provided data structures to the header
	 *
	 * @param jsonObject to copy contents into the header
	 * @return current instance
	 */
	JwtEntityBuilder addHeader(JSONObject jsonObject);

	/**
	 * Adds the provided data structures to the claims
	 *
	 * @param jsonObject to copy contents into the claims
	 * @return current instance
	 */
	JwtEntityBuilder addClaims(JSONObject jsonObject);

}
