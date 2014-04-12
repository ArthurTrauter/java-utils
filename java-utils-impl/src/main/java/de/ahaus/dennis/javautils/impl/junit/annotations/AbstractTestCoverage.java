package de.ahaus.dennis.javautils.impl.junit.annotations;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractTestCoverage {

	private boolean done = false;
	private Map<String, Integer> testCoverage = new HashMap<String, Integer>();

	ClassUnderTest classUnderTest = null;

	interface MethodInterceptor {
		void before(Method method);

		void intercept(Method method);

		void after(Method method);
	}

	abstract class MethodInterceptorAdapter implements MethodInterceptor {

		@Override
		public void before(Method method) {

		}

		@Override
		public void intercept(Method method) {

		}

		@Override
		public void after(Method method) {

		}

	}

	public AbstractTestCoverage() {

		classUnderTest = this.getClass().getAnnotation(ClassUnderTest.class);
	}

	@Test
	public void checkTestMethodsCoverage() {

		if (done) {
			return;
		}

		if (!isClassUnderTestAnnotationExisting()) {
			Assert.fail("No annotation @"
					+ ClassUnderTest.class.getSimpleName() + " found!");
		}

		interceptClassUnderTestDeclaredMethods();

		interceptThisDeclaredMethodsUnderTest();

		testMethodsNotCovered();

		done = true;

	}

	public void testMethodsNotCovered() {

		Iterator<String> it = testCoverage.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			int value = testCoverage.get(key);
			System.out.println("Method calls inside test: ");
			PrintWriter pw = new PrintWriter(System.out);
			pw.printf(classUnderTest.value() + ".%-20s :%s", key, value);
			pw.flush();
			Assert.assertTrue("Method \"" + classUnderTest.value().getName()
					+ "." + key + "\" seems not to be under test because no @"
					+ MethodUnderTest.class.getName()
					+ " declared for that method is found!", value > 0);
		}

	}

	protected boolean isClassUnderTestAnnotationExisting() {

		return !(this.classUnderTest == null);

	}

	protected void interceptThisDeclaredMethodsUnderTest() {

		Method[] methodsUnderTest = getMethodsUnterTest();

		intercept(methodsUnderTest, new MethodInterceptorAdapter() {
			@Override
			public void intercept(Method method) {

				String methodUnderTest = method.getAnnotation(
						MethodUnderTest.class).value();
				addValue(methodUnderTest);

			}
		});

	}

	protected void interceptClassUnderTestDeclaredMethods() {

		Method[] classUnderTestMethods = this.classUnderTest.value()
				.getDeclaredMethods();

		intercept(classUnderTestMethods, new MethodInterceptorAdapter() {
			@Override
			public void intercept(Method method) {

				addValue(method.getName());
			}
		});

	}

	private void addValue(String key) {

		if (testCoverage.containsKey(key)) {
			int val = testCoverage.get(key);
			val++;
			testCoverage.put(key, val);
		} else {
			testCoverage.put(key, 0);
		}

	}

	protected void intercept(Method[] methods, MethodInterceptor interceptor) {

		for (Method method : methods) {
			interceptor.before(method);
			interceptor.intercept(method);
			interceptor.after(method);
		}

	}

	protected Method[] getMethodsUnterTest() {

		List<Method> methods = new ArrayList<Method>();
		Method[] currentClassMethods = this.getClass().getMethods();
		for (Method method : currentClassMethods) {
			if (method.isAnnotationPresent(MethodUnderTest.class)) {
				methods.add(method);
			}
		}
		return toArray(methods);

	}

	private Method[] toArray(List<Method> methods) {

		return methods.toArray(new Method[methods.size()]);
	}
}
