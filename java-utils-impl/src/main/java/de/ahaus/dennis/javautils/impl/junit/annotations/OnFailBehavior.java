package de.ahaus.dennis.javautils.impl.junit.annotations;

import org.junit.Assert;

import de.ahaus.dennis.javautils.impl.junit.TestCoverage;

/**
 * This {@link Enum} holds the possible failure behavior shapes when the unit
 * test in {@link TestCoverage#testCoverage()} fails.
 * <p>
 * Currently there following possibilities how the test execution can notify the
 * environment on failure:
 * <ul>
 * <li>{@link Assert#fail()}</li>
 * <li>throwing following exception: {@link MethodNotUnderTestException}</li>
 * </ul>
 * This enum covers the parameter {@link ClassUnderTest#onFailBehavior()}. When
 * this parameter is set to {@link OnFailBehavior#JUNIT_ASSERT} test failure
 * will cause {@link Assert#fail()} with a explicit message. If
 * {@link OnFailBehavior#EXCEPTION} is set a {@link MethodNotUnderTestException}
 * will be thrown on test failure.
 * <p>
 * The different kind of behaviors can be used to execute different ways of text
 * execution.
 * 
 * @see ClassUnderTest
 * 
 * @author Dennis Ahaus
 * 
 */
public enum OnFailBehavior {

	/**
	 * This parameter affects {@link ClassUnderTest#onFailBehavior()} and
	 * ensures that on coverage test failure the test notifies its runtime
	 * environment by {@link Assert#fail()} about the failure.
	 */
	JUNIT_ASSERT,

	/**
	 * This parameter affects {@link ClassUnderTest#onFailBehavior()} and
	 * ensures that on coverage test failure the test notifies its runtime
	 * environment by throwing a {@link MethodNotUnderTestException}.
	 */
	EXCEPTION

}
