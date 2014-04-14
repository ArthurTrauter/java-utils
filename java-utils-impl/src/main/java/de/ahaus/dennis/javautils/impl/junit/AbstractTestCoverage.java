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
	
	/**
	 * Activities:
	 * <ol>
	 * 	<li>Get methods in class under test</li>
	 * 	<li>Get methods in current test object class</li>
	 * </ol>
	 * @author Dennis Ahaus
	 *
	 */

	class MethodVisitor extends VisitorAdapter<Method> {

		HashMap<String, Integer> coveredMethods = new HashMap<String, Integer>();
		List<Method> notCoveredMethods = new ArrayList<>();

		@Override
		public void visit(Method object) {

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
	private Map<String, Integer> testCoverage = new HashMap<String, Integer>();
	private Object testObject;
	private ClassUnderTest classUnderTest = null;
	MethodVisitor methodVisitor = new MethodVisitor();

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

		visitClassUnterTestMethods(methodVisitor);

		done = true;

	}

	/**
	 * @param visitor
	 */
	protected void visitClassUntertestMethods(Visitor<Method> visitor) {

		HostProviderAdapter<Method> provider = new HostProviderAdapter<Method>() {

			@Override
			protected List<Method> getElements() {

				return Arrays
						.asList(getCurrentClassMethodsWithMethodUnderTestAnnotation());
			}
		};
		List<Method> methods = Arrays
				.asList(getCurrentClassMethodsWithMethodUnderTestAnnotation());
		provider.iterate(methods, methodVisitor);

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

	protected void interateClassUnderTestDeclaredMethods() {

		Method[] classUnderTestMethods = this.classUnderTest.value()
				.getDeclaredMethods();

		MethodHostProvider provider = new MethodHostProvider();
		provider.iterate(Arrays.asList(classUnderTestMethods),
				new VisitorAdapter<Method>() {
					@Override
					public void visit(Method object) {

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
			interceptor.visit(method);
			interceptor.after(method);
		}

	}

	protected Method[] getCurrentClassMethodsWithMethodUnderTestAnnotation() {

		List<Method> methods = new ArrayList<Method>();
		Method[] currentClassMethods = this.getClass().getMethods();
		for (Method method : currentClassMethods) {
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

	protected Method[] getClassUnderTestMethods() {

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
