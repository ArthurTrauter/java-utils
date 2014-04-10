package de.ahaus.dennis.javautils.impl.junit.annotations;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractTestCoverage {

	private boolean done = false;
	private Map<String, Integer> testCoverage = new HashMap<String, Integer>();

	@Test
	public void checkTestMethodsCoverage() {

		if (!done) {

			ClassUnderTest classUnderTest = this.getClass().getAnnotation(
					ClassUnderTest.class);

			if (classUnderTest == null) {
				Assert.fail("No annotation @"
						+ ClassUnderTest.class.getSimpleName() + " found!");
			}

			Method[] classUnderTestMethods = classUnderTest.value()
					.getDeclaredMethods();

			for (Method method : classUnderTestMethods) {
				testCoverage.put(method.getName(), 0);
			}

			Method[] currentClassMethods = this.getClass().getMethods();
			for (Method method : currentClassMethods) {
				if (method.isAnnotationPresent(MethodUnderTest.class)) {
					String key = method.getAnnotation(MethodUnderTest.class)
							.value();
					int val = testCoverage.get(key);
					val++;
					System.out.println(key+"="+val);
					
					if (testCoverage.containsKey(key)) {
						testCoverage.put(key, val);
					}
				}
			}

			Iterator<String> it = testCoverage.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				int value = testCoverage.get(key);
				Assert.assertTrue("Method \"" + classUnderTest.value().getName() +"."+key
						+ "\" seems not to be test because no @"
						+ MethodUnderTest.class.getName()
						+ " for that method is found!", value > 0);
			}

			done = true;
		}

		// System.out.println(c.testClass());
		// System.out.println("------------- methods ");
		//
		// Method[] ms = c.testClass().getDeclaredMethods();
		// for (Method method : ms) {
		// System.out.println(method.getName());
		// }

	}
}
