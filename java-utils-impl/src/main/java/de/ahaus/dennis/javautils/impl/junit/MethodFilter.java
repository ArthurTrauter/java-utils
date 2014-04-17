package de.ahaus.dennis.javautils.impl.junit;

import java.lang.reflect.Method;

public abstract class MethodFilter {

	public abstract boolean accept(Method m);

}
