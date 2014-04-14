package de.ahaus.dennis.javautils.impl.junit;

interface Visitor<T> {

	void before(T object);

	void intercept(T object);

	void after(T object);

}