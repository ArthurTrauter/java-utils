package de.ahaus.dennis.javautils.impl.junit;

interface Visitor<T> {

	void before(T object);

	void visit(T object);

	void after(T object);

}