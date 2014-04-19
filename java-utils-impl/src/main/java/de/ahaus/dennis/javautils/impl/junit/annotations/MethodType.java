package de.ahaus.dennis.javautils.impl.junit.annotations;

/**
 * This {@link Enum} defines the way the test coverage framework scans the
 * methods in the class under test given by {@link ClassUnderTest#value()}
 * parameter.
 * <p>
 * If parameter is set to {@link MethodType#ALL} all methods in class under test
 * will be used to check the coverage against - including those from parent
 * classes. If parameter is set to {@link MethodType#DECLARED} only those
 * methods will be used which are exclusively declared in the class under test.
 * 
 * @author Dennis Ahaus
 * 
 */
public enum MethodType {

	/**
	 * Use all methods in class under test to check the coverage against -
	 * including those from parent classes.
	 */
	ALL,

	/**
	 * Use only declared methods in class under test to check the coverage
	 * against - excluding those from parent classes.
	 */
	DECLARED

}
