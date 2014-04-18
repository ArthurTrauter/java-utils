package de.ahaus.dennis.javautils.impl.iterate;

/**
 * @author Dennis Ahaus
 * 
 * @param <T>
 */
public abstract class Interceptor<T> {

	/**
	 * @param object
	 */
	public void before(T object) {

	}

	/**
	 * @param object
	 */
	public void intercept(T object) {

	}

	/**
	 * @param object
	 */
	public void after(T object) {

	}
}
