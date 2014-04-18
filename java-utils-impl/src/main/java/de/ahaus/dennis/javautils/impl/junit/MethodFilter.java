package de.ahaus.dennis.javautils.impl.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Dennis Ahaus
 * 
 */
public class MethodFilter {

	/**
	 * @author Dennis Ahaus
	 * 
	 */
	public interface Filter {
		/**
		 * @param m
		 * @return
		 */
		boolean accept(Method m);
	}

	private List<Filter> internalFilters = new ArrayList<Filter>();

	/**
	 * @param annotationClass
	 * @return
	 */
	public MethodFilter addAnnotationFilter(
			final Class<? extends Annotation> annotationClass) {

		addInternalFilter(new Filter() {

			@Override
			public boolean accept(Method m) {
				if (m.isAnnotationPresent(annotationClass)) {
					return true;
				}
				return false;
			}
		});
		return this;

	}

	/**
	 * @param value
	 * @return
	 */
	public MethodFilter addMethodNameEqualsFilter(final String value) {

		addInternalFilter(new Filter() {

			@Override
			public boolean accept(Method m) {
				if (m.getName().equals(value)) {
					return true;
				}
				return false;
			}
		});
		return this;

	}

	/**
	 * @param value
	 * @return
	 */
	public MethodFilter addMethodNameEqualsIgnoreCaseFilter(final String value) {

		addInternalFilter(new Filter() {

			@Override
			public boolean accept(Method m) {
				if (m.getName().equalsIgnoreCase(value)) {
					return true;
				}
				return false;
			}
		});
		return this;

	}

	/**
	 * @param filter
	 * @return
	 */
	public MethodFilter addFilter(Filter filter) {

		internalFilters.add(filter);
		return this;

	}

	/**
	 * @param clazz
	 * @return
	 */
	public List<Method> filter(Class<?> clazz) {

		return filter(clazz.getMethods());
	}

	/**
	 * @param object
	 * @return
	 */
	public List<Method> filter(Object object) {

		return filter(object.getClass().getMethods());
	}

	/**
	 * @param m
	 * @return
	 */
	public List<Method> filter(Method[] m) {

		List<Method> methods = Arrays.asList(m);
		return doFilter(methods);
	}

	/**
	 * @param methods
	 * @return
	 */
	protected List<Method> doFilter(List<Method> methods) {
		List<Method> returningMethods = new ArrayList<Method>();
		Iterator<Method> methodIterator = methods.iterator();
		while (methodIterator.hasNext()) {
			Method method = (Method) methodIterator.next();
			if (isAccepted(method)) {
				returningMethods.add(method);
			}
		}
		return returningMethods;
	}

	/**
	 * @param method
	 * @return
	 */
	protected boolean isAccepted(Method method) {
		for (Filter filter : internalFilters) {
			if (!filter.accept(method)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param filter
	 */
	protected void addInternalFilter(Filter filter) {
		this.internalFilters.add(filter);
	}

}