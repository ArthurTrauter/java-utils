package de.ahaus.dennis.javautils.test.junit;

import java.lang.reflect.Method;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import de.ahaus.dennis.javautils.impl.junit.MethodFilter;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;

public class TestMethodFilter {

	MethodFilter filter = new MethodFilter();

	@Test
	@MethodUnderTest("")
	public void testAddAnnotationFilter() {
		List<Method> methods = filter
				.addAnnotationFilter(MethodUnderTest.class).filter(this);
		Assert.assertTrue(
				"Size of methods should be 1 but is " + methods.size(),
				methods.size() == 1);
		Assert.assertThat("Method name should be testAddAnnotationFilter",
				methods.iterator().next().getName(),
				CoreMatchers.is("testAddAnnotationFilter"));
	}

	@Test
	public void testFilterMethodArray() {
		List<Method> methods = filter
				.addAnnotationFilter(MethodUnderTest.class).filter(
						this.getClass().getMethods());
		Assert.assertTrue(
				"Size of methods should be 1 but is " + methods.size(),
				methods.size() == 1);
		Assert.assertThat("Method name should be testAddAnnotationFilter",
				methods.iterator().next().getName(),
				CoreMatchers.is("testAddAnnotationFilter"));
	}

}
