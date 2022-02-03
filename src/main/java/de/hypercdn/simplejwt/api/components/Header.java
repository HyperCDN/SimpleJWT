package de.hypercdn.simplejwt.api.components;

import de.hypercdn.simplejwt.api.helper.CommonKey;
import de.hypercdn.simplejwt.api.helper.JsonContainer;

/**
 * Represents the header part of a jwt
 */
public interface Header extends JsonContainer{

	enum Common implements CommonKey{
		TYPE("typ", String.class),
		CONTENT_TYPE("cty", String.class),
		ALGORITHM("alg", String.class);

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
