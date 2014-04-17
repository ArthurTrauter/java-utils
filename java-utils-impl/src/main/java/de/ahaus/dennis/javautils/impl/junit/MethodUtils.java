package de.ahaus.dennis.javautils.impl.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodUtils {

	public static List<Method> listMethods(Class<?> clazz, MethodFilter filter) {

		List<Method> methods = new ArrayList<>();
		for (Method method : clazz.getMethods()) {
			if (filter.accept(method)) {
				methods.add(method);
			}
		}
		return methods;
	}

}
