package de.hypercdn.simplejwt.api.helper;

import org.json.JSONObject;

public interface JsonContainer {

	/**
	 * Returns the value for a given key
	 *
	 * @param key to access
	 * @param <T> generic
	 * @return value or null
	 */
	<T> T getValueFor(String key);

	/**
	 * Returns the value for a given common key
	 *
	 * @param commonKey to access
	 * @param <T> generic
	 * @return value or null
	 */
	default <T> T getValueFor(CommonKey commonKey){
		return getValueFor(commonKey.getKey());
	}

	/**
	 * Returns the value for a given key tries to cast it to the provided type
	 *
	 * @param key to access
	 * @param tClass of the value type
	 * @param <T> generic
	 * @return value or null
	 */
	<T> T getValueFor(String key, Class<T> tClass);

	/**
	 * Returns the json contents
	 *
	 * @return json
	 */
	JSONObject asJson();

	/**
	 * Returns the json contents as string
	 *
	 * @return json string
	 */
	default String asString(){
		return asJson().toString();
	}

}
