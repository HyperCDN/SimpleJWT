package de.hypercdn.simplejwt.api.components;

import de.hypercdn.simplejwt.api.helper.CommonKey;
import de.hypercdn.simplejwt.api.helper.JsonContainer;

/**
 * Represents the claims part of a jwt
 */
public interface Claims extends JsonContainer{

	enum Common implements CommonKey{
			ISSUER("iss", String.class),
			SUBJECT("sub", String.class),
			AUDIENCE("aud", String.class),
			EXPIRATION_TIME("exp", Long.class),
			NOT_BEFORE("nbf", Long.class),
			ISSUED_AT("iat", Long.class),
			JWT_ID("jti", String.class),
		;

		private final String key;
		private final Class<?> type;

		Common(String key, Class<?> type){
			this.key = key;
			this.type = type;
		}

		@Override
		public String getKey(){
			return key;
		}

		@Override
		public Class<?> getType(){
			return type;
		}

		@Override
		public String toString(){
			return getKey();
		}
	}

}
