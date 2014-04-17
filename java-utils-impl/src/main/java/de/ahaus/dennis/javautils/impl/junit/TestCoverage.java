package de.ahaus.dennis.javautils.impl.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.ahaus.dennis.javautils.impl.iterate.Interceptor;
import de.ahaus.dennis.javautils.impl.iterate.Provider;
import de.ahaus.dennis.javautils.impl.junit.annotations.ClassUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodType;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;

/**
 * @author Dennis Ahaus
 * 
 */
public class TestCoverage {

	Object testClassObject;
	List<MethodCoverage> methodCoverages = new ArrayList<MethodCoverage>();

	public TestCoverage() {
		testClassObject = this;
	}

	public TestCoverage(Object testClassObject) {
		super();
		this.testClassObject = testClassObject;
	}

	@Test
	public void testCoverage() {

		buildMethodCoverage();

		for (MethodCoverage c : methodCoverages) {
			System.out.println(c);
		}

	}

	protected void buildMethodCoverage() {

		new Provider<Method>().iterate(getMethodsInClassUnderTest(),
				new Interceptor<Method>() {
					@Override
					public void intercept(Method m) {
						methodCoverages.add(new MethodCoverage(m));
					}
				});

		new Provider<Method>().iterate(getAnnotatedMethodsInTestClassObject(),
				new Interceptor<Method>() {
					@Override
					public void intercept(Method testMethod) {
						String methodNameValue = testMethod.getAnnotation(
								MethodUnderTest.class).value();
						for (MethodCoverage coverage : methodCoverages) {
							Method converedMethod = coverage.getCoveredMethod();
							if (converedMethod.getName()
									.equals(methodNameValue)) {
								coverage.getCoveredByMethods().add(testMethod);
							}
						}
					}
				});

	}

	/**
	 * @return
	 */
	protected List<Method> getAnnotatedMethodsInTestClassObject() {

		return new MethodFilter().addAnnotationFilter(MethodUnderTest.class)
				.filter(testClassObject);

	}

	/**
	 * @return
	 */
	protected List<Method> getMethodsInClassUnderTest() {

		if (getClassUnderTestMethodType() == MethodType.ALL) {
			return Arrays.asList(getClassUnterTestValue().getMethods());
		} else {
			return Arrays.asList(getClassUnterTestValue().getDeclaredMethods());
		}

	}

	/**
	 * @return
	 */
	protected Class<?> getClassUnterTestValue() {

		return testClassObject.getClass().getAnnotation(ClassUnderTest.class)
				.value();
	}

	/**
	 * @return
	 */
	protected MethodType getClassUnderTestMethodType() {
		return testClassObject.getClass().getAnnotation(ClassUnderTest.class)
				.methods();
	}

}
