package de.hypercdn.simplejwt.imp.components;

import de.hypercdn.simplejwt.api.components.Header;
import org.json.JSONObject;

public class GenericHeader implements Header{

	private final JSONObject header;

	public GenericHeader(JSONObject header){
		this.header = header;
	}

	@Override
	public <T> T getValueFor(String key){
		return (T) header.get(key);
	}

	@Override
	public <T> T getValueFor(String key, Class<T> tClass){
		return tClass.cast(header.get(key));
	}

	@Override
	public JSONObject asJson(){
		return new JSONObject(header.toString());
	}

}
