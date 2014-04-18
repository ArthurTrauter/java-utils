package de.ahaus.dennis.javautils.impl.junit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.ahaus.dennis.javautils.impl.iterate.Interceptor;
import de.ahaus.dennis.javautils.impl.iterate.Provider;
import de.ahaus.dennis.javautils.impl.junit.annotations.ClassUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodNotUnderTestException;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodType;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.OnFailBehavior;

/**
 * @author Dennis Ahaus
 * 
 */
public class TestCoverage {

	Object testClassObject;
	List<MethodCoverage> methodCoverages = new ArrayList<MethodCoverage>();
	boolean coverageHasBeenBuild = false;
	boolean executed = false;

	/**
	 * 
	 */
	public TestCoverage() {

		testClassObject = this;

		doCheckParentClass();
	}

	/**
	 * @param testClassObject
	 */
	public TestCoverage(Object testClassObject) {
		super();
		this.testClassObject = testClassObject;
	}

	/**
	 * 
	 */
	protected void doCheckParentClass() {

		Class<?> clazz = this.getClass().getSuperclass();
		if (clazz != TestCoverage.class) {
			throw new IllegalArgumentException(
					"Usage of this parameterless contructor is only allowed when be a parent of any subclass. "
							+ "Use TestCoverage(Object ...) constructor instead.");
		}

	}

	/**
	 * 
	 */
	@Test
	public void testCoverage() {

		if (executed) {
			return;
		}

		executed = true;

		buildMethodCoverage();

		doCheckCoverage();

	}

	/**
	 * 
	 */
	protected void doCheckCoverage() {

		List<Method> methodsNotCovered = getMethodsNotCovered();
		if (methodsNotCovered.size() > 0) {

			StringWriter writer = new StringWriter();
			PrintWriter pw = new PrintWriter(writer);
			pw.println("Test coverage fails! "
					+ "There are class under test methods not "
					+ "covered by any available test method!");

			pw.println("Methods not covered:");
			for (Method method : methodsNotCovered) {
				pw.println(method.toString());
			}

			String message = writer.getBuffer().toString();

			if (getClassUnderTestOnFailBehavior() == OnFailBehavior.JUNIT_ASSERT) {
				Assert.fail(message);
			} else {
				throw new MethodNotUnderTestException(message);
			}
		}

	}

	/**
	 * @return
	 */
	protected List<Method> getMethodsNotCovered() {

		if (!coverageHasBeenBuild) {
			buildMethodCoverage();
		}

		final List<Method> methodsNotCovered = new ArrayList<Method>();
		new Provider<MethodCoverage>().iterate(methodCoverages,
				new Interceptor<MethodCoverage>() {
					@Override
					public void intercept(MethodCoverage c) {
						if (c.getCoveredByMethods().size() == 0) {
							methodsNotCovered.add(c.getCoveredMethod());
						}
					}
				});

		return methodsNotCovered;

	}

	/**
	 * 
	 */
	protected void buildMethodCoverage() {

		if (methodCoverages == null) {
			methodCoverages = new ArrayList<MethodCoverage>();
		}
		methodCoverages.clear();

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

		coverageHasBeenBuild = true;

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

	/**
	 * @return
	 */
	protected OnFailBehavior getClassUnderTestOnFailBehavior() {
		return testClassObject.getClass().getAnnotation(ClassUnderTest.class)
				.onFailBehavior();
	}

}
