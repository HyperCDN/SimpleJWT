package de.hypercdn.simple.jwt.imp.components;

import de.hypercdn.simple.jwt.api.components.Claims;
import org.json.JSONObject;

public class GenericClaims implements Claims{

	private final JSONObject claims;

	public GenericClaims(JSONObject claims){
		this.claims = claims;
	}

	@Override
	public <T> T getValueFor(String key){
		return (T) claims.get(key);
	}

	@Override
	public <T> T getValueFor(String key, Class<T> tClass){
		return tClass.cast(claims.get(key));
	}

	@Override
	public JSONObject asJson(){
		return new JSONObject(claims.toString());
	}

}
