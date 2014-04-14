package de.ahaus.dennis.javautils.impl.junit;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	private boolean done = false;
	private Map<String, Integer> testCoverage = new HashMap<String, Integer>();
	private Object testObject;
	private ClassUnderTest classUnderTest = null;
	private VisitorAdapter<Method> methodVisitor = new VisitorAdapter<Method>() {

		@Override
		public void intercept(Method object) {

			// TODO Auto-generated method stub
			super.intercept(object);
		}
	};

	public AbstractTestCoverage(Object testObject) {

		this.testObject = testObject;
		classUnderTest = this.testObject.getClass().getAnnotation(
				ClassUnderTest.class);
	}

	public AbstractTestCoverage() {

		classUnderTest = this.getClass().getAnnotation(ClassUnderTest.class);

	}

	@Test
	public void testCheckTestMethodsCoverage() {

		if (done) {
			return;
		}

		if (!isClassUnderTestAnnotationExisting()) {
			Assert.fail("No annotation @"
					+ ClassUnderTest.class.getSimpleName() + " found!");
		}

		MethodHostProvider provider = new MethodHostProvider();
		provider.setElements(getMethodsUnterTest());
		provider.iterate(methodVisitor);

		interateClassUnderTestDeclaredMethods();

		interceptThisDeclaredMethodsUnderTest();

		testMethodsNotCovered();

		done = true;

	}

	protected void testMethodsNotCovered() {

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

	protected void visitClassUnterTestMethods(MethodVisitor visitor) {

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

	protected void interateClassUnderTestDeclaredMethods() {

		Method[] classUnderTestMethods = this.classUnderTest.value()
				.getDeclaredMethods();

		MethodHostProvider provider = new MethodHostProvider();
		provider.iterate(Arrays.asList(classUnderTestMethods),
				new VisitorAdapter<Method>() {
					@Override
					public void intercept(Method object) {

						addValue(object.getName());
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

	protected void intercept(Method[] methods, Visitor interceptor) {

		for (Method method : methods) {
			interceptor.before(method);
			interceptor.intercept(method);
			interceptor.after(method);
		}

	}

	private MethodHostProvider createClassUnderTestMethodsProvider() {

		Method[] classUnderTestMethods = this.classUnderTest.value()
				.getDeclaredMethods();

		MethodHostProvider provider = new MethodHostProvider();
		provider.iterate(elements, visitor);

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
