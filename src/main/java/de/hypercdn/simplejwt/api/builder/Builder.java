package de.hypercdn.simplejwt.api.builder;

public interface Builder<T>{

	/**
	 * Finalizes building the entity
	 *
	 * @return entity which has been built
	 */
	T build();

}
