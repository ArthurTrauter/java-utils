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

	private MethodFilter parentFilter;

	/**
	 * @param annotationClass
	 * @return
	 */
	public MethodFilter addAnnotationFilter(
			final Class<? extends Annotation> annotationClass) {

		MethodFilter filter = new MethodFilter() {

			@Override
			public boolean accept(Method m) {
				if (m.isAnnotationPresent(annotationClass)) {
					return true;
				}
				return false;
			}
		};

		filter.setParentFilter(this);
		return filter;

	}

	// /**
	// * @param annotationClass
	// * @return
	// */
	// public MethodFilter addIsNotDeclaredFilter() {
	//
	// addInternalFilter(new Filter() {
	//
	// @Override
	// public boolean accept(Method m) {
	// try {
	// m.getDeclaringClass().getDeclaredMethod(m.getName(),
	// m.getParameterTypes());
	// return false;
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// return true;
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// return true;
	// }
	// }
	// });
	// return this;
	// }
	//
	// /**
	// * @return
	// */
	// public MethodFilter addIsDeclaredFilter() {
	//
	// addInternalFilter(new Filter() {
	//
	// @Override
	// public boolean accept(Method m) {
	// try {
	// m.getDeclaringClass().getDeclaredMethod(m.getName(),
	// m.getParameterTypes());
	// return true;
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }
	// });
	// return this;
	// }
	//
	// /**
	// * @param modifiers
	// * @return
	// */
	// public MethodFilter addExcludeModifierFilter(final int modifier) {
	//
	// addInternalFilter(new Filter() {
	//
	// @Override
	// public boolean accept(Method m) {
	// int mod = m.getModifiers();
	// if (mod == modifier) {
	// return true;
	// }
	// return false;
	//
	// }
	// });
	// return this;
	// }
	//
	// /**
	// * @param value
	// * @return
	// */
	// public MethodFilter addNameEqualsFilter(final String value) {
	//
	// addInternalFilter(new Filter() {
	//
	// @Override
	// public boolean accept(Method m) {
	// if (m.getName().equals(value)) {
	// return true;
	// }
	// return false;
	// }
	// });
	// return this;
	//
	// }
	//
	// /**
	// * @param value
	// * @return
	// */
	// public MethodFilter addNameEqualsIgnoreCaseFilter(final String value) {
	//
	// addInternalFilter(new Filter() {
	//
	// @Override
	// public boolean accept(Method m) {
	// if (m.getName().equalsIgnoreCase(value)) {
	// return true;
	// }
	// return false;
	// }
	// });
	// return this;
	//
	// }

	/**
	 * @param filter
	 * @return
	 */
	public void setParentFilter(MethodFilter filter) {

		parentFilter = filter;

	}

	public MethodFilter getParentFilter() {
		return parentFilter;
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
			if (isAccepted(this, method)) {
				returningMethods.add(method);
			}
		}
		return returningMethods;
	}

	protected boolean accept(Method m) {
		return true;
	}

	/**
	 * @param method
	 * @return
	 */

	private boolean isAccepted(MethodFilter filter, Method method) {

		if (filter == null) {
			return true;
		} else {
			return filter.accept(method)
					&& isAccepted(filter.getParentFilter(), method);
		}

	}

}
