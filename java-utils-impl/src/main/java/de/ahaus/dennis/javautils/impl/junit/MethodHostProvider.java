package de.ahaus.dennis.javautils.impl.junit;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodHostProvider extends HostProviderAdapter<Method> {

	List<Method> elements;

	@Override
	public List<Method> getElements() {

		return this.elements;
	}

	public void setElements(List<Method> methods) {

		this.elements = methods;
	}

	public void setElements(Method[] methods) {

		setElements(Arrays.asList(methods));
	}

}
