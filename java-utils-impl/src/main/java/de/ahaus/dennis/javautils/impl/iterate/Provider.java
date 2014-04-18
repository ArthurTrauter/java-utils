package de.ahaus.dennis.javautils.impl.iterate;

import java.util.List;

/**
 * @author Dennis Ahaus
 * 
 * @param <T>
 */
public class Provider<T> {

	/**
	 * @param list
	 * @param interceptor
	 */
	public void iterate(List<T> list, Interceptor<T> interceptor) {
		for (T obj : list) {
			interceptor.before(obj);
			interceptor.intercept(obj);
			interceptor.after(obj);
		}
	}

}
