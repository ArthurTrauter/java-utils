package de.ahaus.dennis.javautils.impl.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.ahaus.dennis.javautils.impl.junit.annotations.ClassUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;

public abstract class AbstractTestCoverage {

	// maybe we should use internal object with constructor parameter.
	// this can be used to let the child class to not extend this class
	// we also should impl param to write report to outputStream
	// we also should consider a parameter like failureBehavior=Enums.ASSERT /
	// Enums.execption
	// to make an Assert... or throw an Exception on coverage failure

	/**
	 * Activities:
	 * <ol>
	 * <li>Get methods in class under test</li>
	 * <li>Get methods under test in current test object class</li>
	 * </ol>
	 * 
	 * @author Dennis Ahaus
	 * 
	 */

	class MethodVisitor extends VisitorAdapter<Method> {

		HashMap<String, Integer> coveredMethods = new HashMap<String, Integer>();

		public MethodVisitor(HashMap<String, Integer> coveredMethods) {

			this.coveredMethods = coveredMethods;
		}

		@Override
		public void visit(Method object) {

			addValue(object.getName());
		}

		private void addValue(String key) {

			if (this.coveredMethods.containsKey(key)) {
				int val = this.coveredMethods.get(key);
				val++;
				this.coveredMethods.put(key, val);
			} else {
				this.coveredMethods.put(key, 0);
			}

		}

	}

	private boolean done = false;
	private Object testObject;
	private ClassUnderTest classUnderTest = null;

	public AbstractTestCoverage(Object testObject) {

		this.testObject = testObject;
		classUnderTest = this.testObject.getClass().getAnnotation(
				ClassUnderTest.class);
	}

	public AbstractTestCoverage() {

		classUnderTest = this.getClass().getAnnotation(ClassUnderTest.class);

	}

	@Test
	public void testMethodCoverage() {

		if (done) {
			return;
		}

		if (!isClassUnderTestAnnotationExisting()) {
			Assert.fail("No annotation @"
					+ ClassUnderTest.class.getSimpleName() + " found!");
		}

		HashMap<String, Integer> coveredMethods = new HashMap<String, Integer>();
		MethodVisitor visitor = new MethodVisitor(coveredMethods);

		List<HostProvider<Method>> providers = createProviders();
		for (HostProvider<Method> hostProvider : providers) {
			hostProvider.iterate(visitor);
		}
		System.out.println(coveredMethods);

		done = true;

	}

	protected List<HostProvider<Method>> createProviders() {

		List<HostProvider<Method>> providers = new ArrayList<HostProvider<Method>>();
		providers.add(createMethodsInClassUnderTestProvider());
		providers.add(createMethodsAnnotatedUnderTestProvider());
		return providers;

	}

	protected HostProvider<Method> createMethodsAnnotatedUnderTestProvider() {

		return new HostProviderAdapter<Method>() {

			@Override
			protected List<Method> getElements() {

				return Arrays.asList(getMethodsAnnotatedUnderTest());
			}
		};

	}

	protected HostProvider<Method> createMethodsInClassUnderTestProvider() {

		return new HostProviderAdapter<Method>() {

			@Override
			protected List<Method> getElements() {

				return Arrays.asList(getMethodsInClassUnderTest());
			}
		};

	}

	protected boolean isClassUnderTestAnnotationExisting() {

		return !(this.classUnderTest == null);

	}

	protected Method[] getMethodsAnnotatedUnderTest() {

		List<Method> methods = new ArrayList<Method>();
		Method[] currentClassUnderTestMethods = this.getClass().getMethods();
		for (Method method : currentClassUnderTestMethods) {
			if (method.isAnnotationPresent(MethodUnderTest.class)) {
				methods.add(method);
			}
		}
		return toArray(methods);

	}

	protected String getMethodUnderTestAnnotationValue(Method method) {

		if (method.isAnnotationPresent(MethodUnderTest.class)) {
			return method.getAnnotation(MethodUnderTest.class).value();
		}
		return null;
	}

	protected Method[] getMethodsInClassUnderTest() {

		Method[] classUnderTestMethods = this.classUnderTest.value()
				.getDeclaredMethods();
		return classUnderTestMethods;

	}

	/**
	 * Generate an array from given {@link List}
	 * 
	 * @param methods
	 * @return
	 */
	private Method[] toArray(List<Method> methods) {

		return methods.toArray(new Method[methods.size()]);
	}

}
